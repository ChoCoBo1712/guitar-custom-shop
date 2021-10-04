package com.chocobo.customshop.web.command;


/**
 * {@code SessionAttribute} class contains constant strings that are stored as {@link jakarta.servlet.http.HttpSession} attributes.
 * @author Evgeniy Sokolchik
 */
public final class SessionAttribute {

    public static final String USER_ID = "userId";
    public static final String USER_LOGIN = "userLogin";
    public static final String USER_ROLE = "userRole";
    public static final String USER_EMAIL = "userEmail";
    public static final String LOCALE = "locale";

    private SessionAttribute() {

    }
}
