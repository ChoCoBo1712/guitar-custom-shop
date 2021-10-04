package com.chocobo.customshop.model.pool;

import com.chocobo.customshop.exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * {@code ConnectionFactory} class is used for internal creating connections via JDBC driver.
 * @author Evgeniy Sokolchik
 */
class ConnectionFactory {

    private static final Logger logger = LogManager.getLogger();
    private static final String DB_PROPERTIES_NAME = "properties/db.properties";
    private static final String DRIVER_PROPERTY = "driver";
    private static final String URL_PROPERTY = "url";

    private static final String dbUrl;
    private static final Properties dbProperties = new Properties();

    static {
        ClassLoader classLoader = ConnectionFactory.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(DB_PROPERTIES_NAME)) {
            dbProperties.load(inputStream);
            Class.forName(dbProperties.getProperty(DRIVER_PROPERTY));
            dbUrl = dbProperties.getProperty(URL_PROPERTY);
        } catch (IOException e) {
            logger.fatal("Couldn't read database properties file", e);
            throw new RuntimeException("Couldn't read database properties file", e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Couldn't register database driver", e);
            throw new RuntimeException("Couldn't read database properties file", e);
        }
    }

    /**
     * Create a database connection.
     * {@link Connection} implementation is actually a {@link ProxyConnection}.
     *
     * @return {@code Connection} instance.
     * @throws DatabaseConnectionException if application is unable to establish a database connection.
     * @see ProxyConnection
     */
    static Connection createConnection() throws DatabaseConnectionException {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbProperties);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            logger.error("Unable to create database connection", e);
            throw new DatabaseConnectionException("Unable to create database connection", e);
        }
    }
}
