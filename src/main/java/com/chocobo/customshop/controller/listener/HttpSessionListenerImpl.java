package com.chocobo.customshop.controller.listener;

import com.chocobo.customshop.controller.command.SessionAttribute;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    private static final String DEFAULT_LOCALE = "ru_RU";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
    }
}
