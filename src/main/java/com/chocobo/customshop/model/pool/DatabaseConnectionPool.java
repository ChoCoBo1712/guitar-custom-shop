package com.chocobo.customshop.model.pool;

import com.chocobo.customshop.exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code DatabaseConnectionPool} class is a thread-safe database connection pool.
 * @author Evgeniy Sokolchik
 */
public class DatabaseConnectionPool {

    public static final Logger logger = LogManager.getLogger();
    private static final String POOL_PROPERTIES_NAME = "properties/pool.properties";
    private static final String MIN_POOL_SIZE_PROPERTY = "minPoolSize";
    private static final String MAX_POOL_SIZE_PROPERTY = "maxPoolSize";
    public static final String CONNECTION_LIFETIME_PROPERTY = "connectionLifetime";
    public static final String POOL_CHECK_DELAY_PROPERTY = "poolCheckDelay";
    public static final String POOL_CHECK_PERIOD_PROPERTY = "poolCheckPeriod";

    private static final AtomicReference<DatabaseConnectionPool> instance = new AtomicReference<>(null);
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private final int minPoolSize;
    private final int maxPoolSize;
    private final long connectionLifetime;

    private final Lock poolLock = new ReentrantLock(true);
    private final BlockingQueue<Connection> availableConnections;
    private final Queue<Connection> usedConnections;

    // TODO: 26.09.2021 ask about instantiation
    /**
     * Get instance of {@code DatabaseConnectionPool} class.
     *
     * @return {@code DatabaseConnectionPool} instance.
     */
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
        int poolCheckDelay;
        int poolCheckPeriod;
        try (InputStream inputStream = classLoader.getResourceAsStream(POOL_PROPERTIES_NAME)) {
            Properties poolProperties = new Properties();
            poolProperties.load(inputStream);

            minPoolSize = Integer.parseInt(poolProperties.getProperty(MIN_POOL_SIZE_PROPERTY));
            maxPoolSize = Integer.parseInt(poolProperties.getProperty(MAX_POOL_SIZE_PROPERTY));

            if (minPoolSize > maxPoolSize) {
                logger.fatal("Min size of pool is greater than max size");
                throw new RuntimeException("Min size of pool is greater than max size");
            }

            connectionLifetime = Long.parseLong(poolProperties.getProperty(CONNECTION_LIFETIME_PROPERTY));
            poolCheckDelay = Integer.parseInt(poolProperties.getProperty(POOL_CHECK_DELAY_PROPERTY));
            poolCheckPeriod = Integer.parseInt(poolProperties.getProperty(POOL_CHECK_PERIOD_PROPERTY));

            availableConnections = new ArrayBlockingQueue<>(maxPoolSize);
            usedConnections = new ArrayDeque<>(maxPoolSize);

            for (int i = 0; i < minPoolSize; i++) {
                Connection connection = ConnectionFactory.createConnection();
                availableConnections.add(connection);
            }
        } catch (IOException e) {
            logger.fatal("Couldn't read connection pool properties file", e);
            throw new RuntimeException("Couldn't read connection pool properties file", e);
        } catch (NumberFormatException e) {
            logger.fatal("Couldn't configure vital parameters of connection pool", e);
            throw new RuntimeException("Couldn't configure size of connection pool", e);
        } catch (DatabaseConnectionException e) {
            logger.fatal("Error establishing connection pool", e);
            throw new RuntimeException("Error establishing connection pool", e);
        }

        PoolTimerTask task = new PoolTimerTask();
        Timer timer = new Timer(true);
        timer.schedule(task, poolCheckDelay, poolCheckPeriod);
    }

    /**
     * Get a database connection.
     * This method has lock in it, so calling thread has to wait until pool has available connections.
     *
     * @return {@code Connection} instance.
     * @throws DatabaseConnectionException if application is unable to establish proper connection.
     */
    public Connection getConnection() throws DatabaseConnectionException {
        try {
            poolLock.lock();
            Connection connection = null;

            if (availableConnections.isEmpty() && usedConnections.size() < maxPoolSize) {
                availableConnections.add(ConnectionFactory.createConnection());
            }

            try {
                connection = availableConnections.take();
                usedConnections.add(connection);
                ProxyConnection proxyConnection = (ProxyConnection) connection;
                proxyConnection.setLifetimeStart(Instant.now());
            } catch (InterruptedException e) {
                logger.error("Unexpected exception", e);
                Thread.currentThread().interrupt();
            } 
            return connection;
        } finally {
            poolLock.unlock();
        }
    }

    /**
     * Release connection and return it to the pool thread-safely.
     *
     * @param connection {@code Connection} instance that must be returned to the available connections queue.
     */
    public void releaseConnection(Connection connection) {
        try {
            poolLock.lock();

            try {
                usedConnections.remove(connection);
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                logger.error("Unexpected exception", e);
                Thread.currentThread().interrupt();
            }
        } finally {
            poolLock.unlock();
        }
    }

    /**
     * Destroy pool and free all resources.
     */
    public void destroy() {
        try {
            poolLock.lock();
            for (int i = 0; i < availableConnections.size(); i++) {
                try {
                    ProxyConnection connection = (ProxyConnection) availableConnections.remove();
                    connection.closeInnerConnection();
                } catch (SQLException e) {
                    logger.error("Failed to close connection", e);
                }
            }

            for (int i = 0; i < usedConnections.size(); i++) {
                try {
                    ProxyConnection connection = (ProxyConnection) usedConnections.remove();
                    connection.closeInnerConnection();
                } catch (SQLException e) {
                    logger.error("Failed to close connection", e);
                }
            }
        } finally {
            poolLock.unlock();
        }

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

    Lock getPoolLock() {
        return poolLock;
    }

    BlockingQueue<Connection> getAvailableConnections() {
        return availableConnections;
    }

    Queue<Connection> getUsedConnections() {
        return usedConnections;
    }

    int getMinPoolSize() {
        return minPoolSize;
    }

    int getMaxPoolSize() {
        return maxPoolSize;
    }

    long getConnectionLifetime() {
        return connectionLifetime;
    }
}
