package com.chocobo.customshop.web.listener;

import com.chocobo.customshop.model.pool.DatabaseConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code ServletContextListenerImpl} class is an implementation of {@link ServletContextListener} interface.
 * It initializes the {@code DatabaseConnectionPool}.
 * @author Evgeniy Sokolchik
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    public static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Connection pool destroyed");
        DatabaseConnectionPool.getInstance().destroy();
    }
}
