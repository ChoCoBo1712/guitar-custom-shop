package com.chocobo.customshop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.chocobo.customshop.controller.command.SessionAttribute.*;

@WebFilter(filterName = "EntryFilter")
public class EntryFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        request.setAttribute(LOGIN_ERROR, session.getAttribute(LOGIN_ERROR));
        request.setAttribute(DUPLICATE_EMAIL_ERROR, session.getAttribute(DUPLICATE_EMAIL_ERROR));
        request.setAttribute(DUPLICATE_LOGIN_ERROR, session.getAttribute(DUPLICATE_LOGIN_ERROR));
        request.setAttribute(INVALID_EMAIL_PATTERN_ERROR, session.getAttribute(INVALID_EMAIL_PATTERN_ERROR));
        request.setAttribute(INVALID_LOGIN_PATTERN_ERROR, session.getAttribute(INVALID_LOGIN_PATTERN_ERROR));
        request.setAttribute(INVALID_PASSWORD_PATTERN_ERROR, session.getAttribute(INVALID_PASSWORD_PATTERN_ERROR));
        request.setAttribute(INVALID_NAME_PATTERN_ERROR, session.getAttribute(INVALID_NAME_PATTERN_ERROR));
        request.setAttribute(INVALID_COLOR_PATTERN_ERROR, session.getAttribute(INVALID_COLOR_PATTERN_ERROR));

        session.removeAttribute(LOGIN_ERROR);
        session.removeAttribute(DUPLICATE_EMAIL_ERROR);
        session.removeAttribute(DUPLICATE_LOGIN_ERROR);
        session.removeAttribute(INVALID_EMAIL_PATTERN_ERROR);
        session.removeAttribute(INVALID_LOGIN_PATTERN_ERROR);
        session.removeAttribute(INVALID_PASSWORD_PATTERN_ERROR);
        session.removeAttribute(INVALID_NAME_PATTERN_ERROR);
        session.removeAttribute(INVALID_COLOR_PATTERN_ERROR);

        chain.doFilter(request, response);
    }
}
