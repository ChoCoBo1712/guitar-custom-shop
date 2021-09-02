package com.chocobo.customshop.controller.command;

public final class PagePath {

    public static final String INDEX_JSP = "/pages/index.jsp";
    public static final String LOGIN_JSP = "/pages/login.jsp";
    public static final String REGISTER_JSP = "/pages/register.jsp";
    public static final String REGISTER_SUCCESS_JSP = "/pages/register_success.jsp";
    public static final String ERROR_500_JSP = "/pages/error500.jsp";
    public static final String ERROR_404_JSP = "/pages/error404.jsp";
    public static final String ACTIVATION_SUCCESS_JSP = "/pages/activation_success.jsp";

    public static final String LOGIN_URL = "/controller?command=go_to_login_page";
    public static final String INDEX_URL = "/controller?command=go_to_index_page";
    public static final String REGISTER_URL = "/controller?command=go_to_register_page";
    public static final String REGISTER_SUCCESS_URL = "/controller?command=go_to_register_success_page";
    public static final String ACTIVATION_SUCCESS_URL = "/controller?command=go_to_activation_success_page";

    private PagePath() {

    }
}
