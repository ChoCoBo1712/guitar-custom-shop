package com.chocobo.customshop.controller.command.validator;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.service.UserService;
import com.chocobo.customshop.service.impl.UserServiceImpl;

import static com.chocobo.customshop.controller.command.SessionAttribute.INVALID_EMAIL_ERROR;
import static com.chocobo.customshop.controller.command.SessionAttribute.INVALID_LOGIN_ERROR;

public class UserValidator {

    public static final String SERVICE_EXCEPTION = "serviceException";

    public static ValidationResult validateRegistration(String email, String login) {
        UserService userService = UserServiceImpl.getInstance();
        ValidationResult validationResult = new ValidationResult();
        try {
            if (!userService.isEmailUnique(email)) {
                validationResult.addToErrorList(INVALID_EMAIL_ERROR);
            }

            if (!userService.isLoginUnique(login)) {
                validationResult.addToErrorList(INVALID_LOGIN_ERROR);
            }
        } catch (ServiceException e) {
            validationResult.addToErrorList(SERVICE_EXCEPTION);
        }
        return validationResult;
    }
}
