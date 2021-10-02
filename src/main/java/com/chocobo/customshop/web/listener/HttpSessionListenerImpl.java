package com.chocobo.customshop.web.listener;

import com.chocobo.customshop.web.command.AppRole;
import com.chocobo.customshop.web.command.SessionAttribute;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    private static final String DEFAULT_LOCALE = "en_US";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.USER_ROLE, AppRole.GUEST);
    }
}
