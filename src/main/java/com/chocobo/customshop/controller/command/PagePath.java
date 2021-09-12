package com.chocobo.customshop.controller.command;

public final class PagePath {

    public static final String INDEX_JSP = "/pages/common/users.jsp";
    public static final String LOGIN_JSP = "/pages/common/login.jsp";
    public static final String REGISTER_JSP = "/pages/common/register.jsp";
    public static final String REGISTER_SUCCESS_JSP = "/pages/common/register_success.jsp";
    public static final String ACTIVATION_SUCCESS_JSP = "/pages/common/activation_success.jsp";
    public static final String ADMIN_USERS_JSP = "/pages/admin/users.jsp";
    public static final String ADMIN_WOODS_JSP = "/pages/admin/woods.jsp";
    public static final String ADMIN_EDIT_USER_JSP = "/pages/admin/edit_user.jsp";
    public static final String ADMIN_CREATE_USER_JSP = "/pages/admin/create_user.jsp";

    public static final String LOGIN_URL = "/controller?command=go_to_login_page";
    public static final String INDEX_URL = "/controller?command=go_to_index_page";
    public static final String REGISTER_URL = "/controller?command=go_to_register_page";
    public static final String REGISTER_SUCCESS_URL = "/controller?command=go_to_register_success_page";
    public static final String ACTIVATION_SUCCESS_URL = "/controller?command=go_to_activation_success_page";
    public static final String ADMIN_USERS_URL = "/controller?command=go_to_users_page";
    public static final String ADMIN_WOODS_URL = "/controller?command=go_to_woods_page";
    public static final String ADMIN_EDIT_USER_URL = "/controller?command=go_to_edit_user_page";
    public static final String ADMIN_CREATE_USER_URL = "/controller?command=go_to_create_user_page";

    private PagePath() {

    }
}
