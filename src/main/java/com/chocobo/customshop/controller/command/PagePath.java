package com.chocobo.customshop.controller.command;

public final class PagePath {

    public static final String INDEX_JSP = "/pages/common/index.jsp";
    public static final String LOGIN_JSP = "/pages/common/login.jsp";
    public static final String REGISTER_JSP = "/pages/common/register.jsp";
    public static final String REGISTER_SUCCESS_JSP = "/pages/common/register_success.jsp";
    public static final String ACTIVATION_SUCCESS_JSP = "/pages/common/activation_success.jsp";
    public static final String ADMIN_USERS_JSP = "/pages/admin/user/users.jsp";
    public static final String ADMIN_EDIT_USER_JSP = "/pages/admin/user/edit_user.jsp";
    public static final String ADMIN_CREATE_USER_JSP = "/pages/admin/user/create_user.jsp";
    public static final String ADMIN_WOODS_JSP = "/pages/admin/wood/woods.jsp";
    public static final String ADMIN_CREATE_WOOD_JSP = "/pages/admin/wood/create_wood.jsp";
    public static final String ADMIN_EDIT_WOOD_JSP = "/pages/admin/wood/edit_wood.jsp";

    public static final String LOGIN_URL = "/controller?command=go_to_login_page";
    public static final String INDEX_URL = "/controller?command=go_to_index_page";
    public static final String REGISTER_URL = "/controller?command=go_to_register_page";
    public static final String REGISTER_SUCCESS_URL = "/controller?command=go_to_register_success_page";
    public static final String ACTIVATION_SUCCESS_URL = "/controller?command=go_to_activation_success_page";
    public static final String ADMIN_USERS_URL = "/controller?command=go_to_users_page";
    public static final String ADMIN_EDIT_USER_URL = "/controller?command=go_to_edit_user_page";
    public static final String ADMIN_CREATE_USER_URL = "/controller?command=go_to_create_user_page";
    public static final String ADMIN_WOODS_URL = "/controller?command=go_to_woods_page";
    public static final String ADMIN_CREATE_WOOD_URL = "/controller?command=go_to_create_wood_page";
    public static final String ADMIN_EDIT_WOOD_URL = "/controller?command=go_to_edit_wood_page";

    public static final String AMPERSAND = "&";
    public static final String EQUALS_SIGN = "=";

    private PagePath() {

    }
}
