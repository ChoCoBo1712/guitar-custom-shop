package com.chocobo.customshop.controller.listener;

import com.chocobo.customshop.controller.command.SessionAttribute;
import com.chocobo.customshop.model.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    private static final String DEFAULT_LOCALE = "en_US";

     @Override
     public void sessionCreated(HttpSessionEvent event) {
         HttpSession session = event.getSession();
         session.setAttribute(SessionAttribute.USER_ROLE, User.UserRole.GUEST);
         session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
     }
}
