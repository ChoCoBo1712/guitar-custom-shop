package com.chocobo.customshop.controller.filter;

import com.chocobo.customshop.controller.command.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "JspFilter")
public class JspFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.getRequestDispatcher(PagePath.ERROR_404_JSP).forward(request, response);
        chain.doFilter(request, response);
    }
}
