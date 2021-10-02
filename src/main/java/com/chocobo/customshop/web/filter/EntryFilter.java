package com.chocobo.customshop.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.chocobo.customshop.web.command.SessionAttribute.*;

@WebFilter(filterName = "EntryFilter")
public class EntryFilter implements Filter {

    private static final String DEFAULT_LOCALE = "en_US";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Cookie[] cookies = httpRequest.getCookies();

        if (cookies != null) {
            Optional<String> optionalLocale = Arrays.stream(cookies)
                    .filter(key -> StringUtils.equals(key.getName(), "locale"))
                    .map(Cookie::getValue)
                    .findAny();
            if (optionalLocale.isPresent()) {
                String locale = optionalLocale.get();
                if (session.getAttribute(LOCALE) != locale) {
                    session.setAttribute(LOCALE, locale);
                }
            } else {
                session.setAttribute(LOCALE, DEFAULT_LOCALE);
            }
        } else {
            session.setAttribute(LOCALE, DEFAULT_LOCALE);
        }

        request.setAttribute(LOGIN_ERROR, session.getAttribute(LOGIN_ERROR));
        request.setAttribute(EMAIL_CONFIRMATION, session.getAttribute(EMAIL_CONFIRMATION));
        request.setAttribute(PASSWORD_CHANGE, session.getAttribute(PASSWORD_CHANGE));
        request.setAttribute(FORGOT_PASSWORD_ERROR, session.getAttribute(FORGOT_PASSWORD_ERROR));
        request.setAttribute(DUPLICATE_EMAIL_ERROR, session.getAttribute(DUPLICATE_EMAIL_ERROR));
        request.setAttribute(DUPLICATE_LOGIN_ERROR, session.getAttribute(DUPLICATE_LOGIN_ERROR));
        request.setAttribute(VALIDATION_ERROR, session.getAttribute(VALIDATION_ERROR));
        request.setAttribute(PROFILE_UPDATED, session.getAttribute(PROFILE_UPDATED));

        session.removeAttribute(LOGIN_ERROR);
        session.removeAttribute(EMAIL_CONFIRMATION);
        session.removeAttribute(PASSWORD_CHANGE);
        session.removeAttribute(FORGOT_PASSWORD_ERROR);
        session.removeAttribute(DUPLICATE_EMAIL_ERROR);
        session.removeAttribute(DUPLICATE_LOGIN_ERROR);
        session.removeAttribute(VALIDATION_ERROR);
        session.removeAttribute(PROFILE_UPDATED);

        chain.doFilter(request, response);
    }
}
