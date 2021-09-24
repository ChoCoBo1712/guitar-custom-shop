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
        request.setAttribute(VALIDATION_ERROR, session.getAttribute(VALIDATION_ERROR));

        session.removeAttribute(LOGIN_ERROR);
        session.removeAttribute(DUPLICATE_EMAIL_ERROR);
        session.removeAttribute(DUPLICATE_LOGIN_ERROR);
        session.removeAttribute(VALIDATION_ERROR);

        chain.doFilter(request, response);
    }
}
