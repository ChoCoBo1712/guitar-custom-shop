package com.chocobo.customshop.controller.command;

public enum CommandType {

    // GoTo commands
    GO_TO_INDEX_PAGE,
    GO_TO_LOGIN_PAGE,
    GO_TO_REGISTER_PAGE,
    GO_TO_REGISTER_SUCCESS_PAGE,
    GO_TO_ACTIVATION_SUCCESS_PAGE,

    // other commands
    REGISTER,
    LOGIN,
    LOGOUT,
    CONFIRM_EMAIL,
}
