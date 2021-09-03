package com.chocobo.customshop.controller.listener;

import com.chocobo.customshop.controller.command.SessionAttribute;
import com.chocobo.customshop.model.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    public static final Logger logger = LogManager.getLogger();

    private static final String DEFAULT_LOCALE = "en_US";

     @Override
     public void sessionCreated(HttpSessionEvent event) {
         logger.info("Session created");
         HttpSession session = event.getSession();
         session.setAttribute(SessionAttribute.USER_ROLE, User.UserRole.GUEST);
         session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
     }
}
