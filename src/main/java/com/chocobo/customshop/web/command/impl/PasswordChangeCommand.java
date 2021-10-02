package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.PasswordValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.PASSWORD_CHANGE;
import static com.chocobo.customshop.web.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class PasswordChangeCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> passwordValidator = PasswordValidator.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = request.getParameter(TOKEN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        try {
            boolean valid = passwordValidator.validate(password);
            if (valid) {
                Optional<User> optionalUser = userService.findByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    userService.updateWithPassword(user, password);

                    session.setAttribute(PASSWORD_CHANGE, true);
                    return CommandResult.createRedirectResult(TOKEN_SUCCESS_URL);
                } else {
                    logger.error("User with email given in token is not found");
                    return CommandResult.createErrorResult(SC_NOT_FOUND);
                }
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                String currentFormPageUrl = PASSWORD_CHANGE_URL + AMPERSAND + TOKEN + EQUALS_SIGN + token;
                return CommandResult.createRedirectResult(currentFormPageUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during password change command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
