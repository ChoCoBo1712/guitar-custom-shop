package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.PagePath;
import com.chocobo.customshop.controller.command.validator.UserValidator;
import com.chocobo.customshop.controller.command.validator.ValidationResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.RequestParameter.*;

public class RegistrationCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        ValidationResult validationResult = UserValidator.validateRegistration(email, login);
        if (validationResult.isValid()) {
            try {
                UserServiceImpl.getInstance().register(email, login, password);
            } catch (ServiceException e) {
                return PagePath.ERROR_500_JSP;
            }
            return PagePath.CONFIRMATION_JSP;
        } else {
            request.setAttribute(VALIDATION_ERROR, validationResult.getErrorMessage());
            return PagePath.REGISTER_JSP;
        }
    }
}
