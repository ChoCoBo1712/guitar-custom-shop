package com.chocobo.customshop.controller.command;

public enum CommandType {

    // GoTo commands
    GO_TO_INDEX_PAGE,
    GO_TO_LOGIN_PAGE,
    GO_TO_REGISTER_PAGE,
    GO_TO_REGISTER_SUCCESS_PAGE,
    GO_TO_ACTIVATION_SUCCESS_PAGE,
    GO_TO_USERS_PAGE,
    GO_TO_CREATE_USER_PAGE,
    GO_TO_EDIT_USER_PAGE,
    GO_TO_WOODS_PAGE,

    // other commands
    REGISTER,
    LOGIN,
    LOGOUT,
    CONFIRM_EMAIL,
    GET_USERS,
    CREATE_USER,
    UPDATE_USER,
    DELETE_USER,
    GET_WOODS,
}
