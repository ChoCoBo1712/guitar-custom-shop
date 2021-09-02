package com.chocobo.customshop.model.pool;

import com.chocobo.customshop.exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DatabaseConnectionPool {

    public static final Logger logger = LogManager.getLogger();
    private static final String POOL_PROPERTIES_NAME = "properties/pool.properties";
    private static final String POOL_SIZE_PROPERTY = "poolSize";

    private static final AtomicReference<DatabaseConnectionPool> instance = new AtomicReference<>(null);
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private final int poolSize;

    private final Lock poolLock = new ReentrantLock(true);
    private final BlockingQueue<Connection> availableConnections;
    private final Queue<Connection> usedConnections;

    public static DatabaseConnectionPool getInstance() {
        while (instance.get() == null) {
            if (isInitialized.compareAndSet(false, true)) {
                instance.set(new DatabaseConnectionPool());
            }
        }
        return instance.get();
    }

    private DatabaseConnectionPool() {
        ClassLoader classLoader = ConnectionFactory.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(POOL_PROPERTIES_NAME)) {
            Properties poolProperties = new Properties();
            poolProperties.load(inputStream);

            poolSize = Integer.parseInt(poolProperties.getProperty(POOL_SIZE_PROPERTY));
            availableConnections = new ArrayBlockingQueue<>(poolSize);
            usedConnections = new ArrayDeque<>(poolSize);
        } catch (IOException e) {
            logger.error("Couldn't read connection pool properties file", e);
            throw new RuntimeException("Couldn't read connection pool properties file", e);
        }
    }

    public Connection getConnection() throws DatabaseConnectionException {
        try {
            poolLock.lock();
            Connection connection = null;

            if (availableConnections.size() + usedConnections.size() < poolSize) {
                availableConnections.add(ConnectionFactory.createConnection());
            }

            try {
                connection = availableConnections.take();
                usedConnections.add(connection);
            } catch (InterruptedException e) {
                logger.error("Unexpected exception", e);
                Thread.currentThread().interrupt();
            } 
            return connection;
        } finally {
            poolLock.unlock();
        }
    }

    public boolean releaseConnection(Connection connection) {
        try {
            poolLock.lock();

            try {
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                logger.error("Unexpected exception", e);
                Thread.currentThread().interrupt();
            }

            return usedConnections.remove(connection);
        } finally {
            poolLock.unlock();
        }
    }

    public void destroy() {
        poolLock.lock();
        for (int i = 0; i < availableConnections.size(); i++) {
            try {
                ProxyConnection connection = (ProxyConnection) availableConnections.take();
                connection.closeInnerConnection();
            } catch (SQLException e) {
                logger.error("Failed to close connection", e);
            } catch (InterruptedException e) {
                logger.error("Unexpected exception", e);
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 0; i < usedConnections.size(); i++) {
            ProxyConnection connection = (ProxyConnection) usedConnections.remove();
            try {
                connection.closeInnerConnection();
            } catch (SQLException e) {
                logger.error("Failed to close connection", e);
            }
        }
        poolLock.unlock();

        DriverManager.getDrivers()
                .asIterator()
                .forEachRemaining(driver -> {
                    try {
                        DriverManager.deregisterDriver(driver);
                    } catch (SQLException e) {
                        logger.error("Failed to deregister driver: ", e);
                    }
                });
    }
}
