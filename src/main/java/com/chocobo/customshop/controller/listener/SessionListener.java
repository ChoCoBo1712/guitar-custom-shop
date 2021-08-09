package com.chocobo.customshop.controller.listener;

import com.chocobo.customshop.controller.command.SessionAttribute;
import com.chocobo.customshop.model.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

     @Override
     public void sessionCreated(HttpSessionEvent e) {
         e.getSession().setAttribute(SessionAttribute.USER_ROLE, User.UserRole.GUEST);
     }
}
