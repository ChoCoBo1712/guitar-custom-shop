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

@WebFilter(filterName = "LocaleFilter")
public class LocaleFilter implements Filter {

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

        chain.doFilter(request, response);
    }
}
