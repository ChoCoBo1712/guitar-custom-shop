package com.chocobo.customshop.controller.listener;

import com.chocobo.customshop.model.pool.DatabaseConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServletContextListenerImpl implements ServletContextListener {

    // TODO: 06.09.2021 ask about listener class usage
    public static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnectionPool.getInstance().destroy();
        logger.info("Connection pool destroyed");
    }
}
