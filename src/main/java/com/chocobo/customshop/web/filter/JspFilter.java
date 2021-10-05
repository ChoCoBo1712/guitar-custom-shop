package com.chocobo.customshop.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


/**
 * {@code JspFilter} class is an implementation of {@link Filter} interface.
 * It filters access of users to all {@code .jsp} files.
 * @author Evgeniy Sokolchik
 */
@WebFilter(filterName = "JspFilter")
public class JspFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
