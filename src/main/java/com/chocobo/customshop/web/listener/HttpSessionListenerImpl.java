package com.chocobo.customshop.web.listener;

import com.chocobo.customshop.web.command.AppRole;
import com.chocobo.customshop.web.command.SessionAttribute;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * {@code HttpSessionListenerImpl} class is an implementation of {@link HttpSessionListener} interface.
 * It sets user application role for newly created session to GUEST value of {@code AppRole} enum.
 * @author Evgeniy Sokolchik
 */
@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute(SessionAttribute.USER_ROLE, AppRole.GUEST);
    }
}
