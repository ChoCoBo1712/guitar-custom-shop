package com.chocobo.customshop.controller.command.validator;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.service.UserService;
import com.chocobo.customshop.service.impl.UserServiceImpl;

public class UserValidator {

    // TODO: 31.08.2021 translate 
    private static final String INVALID_EMAIL_MSG = "Such email is already registered";
    private static final String INVALID_LOGIN_MSG = "User with such login is already registered";
    private static final String EXCEPTION_MSG = "Could not validate given information now, please try later";

    public static ValidationResult validateRegistration(String email, String login) {
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (!userService.isEmailUnique(email)) {
                return new ValidationResult(INVALID_EMAIL_MSG);
            }

            if (!userService.isLoginUnique(login)) {
                return new ValidationResult(INVALID_LOGIN_MSG);
            }
        } catch (ServiceException e) {
            return new ValidationResult(EXCEPTION_MSG);
        }
        return new ValidationResult();
    }
}
