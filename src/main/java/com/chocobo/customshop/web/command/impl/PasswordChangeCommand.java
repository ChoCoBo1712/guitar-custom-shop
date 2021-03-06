package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.PasswordValidator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class PasswordChangeCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> passwordValidator = PasswordValidator.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
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

                    String redirectUrl = INDEX_URL
                            + AMPERSAND + PASSWORD_CHANGE_SUCCESS + EQUALS_SIGN + true;
                    return CommandResult.createRedirectResult(redirectUrl);
                } else {
                    logger.error("User with email given in token is not found");
                    return CommandResult.createErrorResult(SC_NOT_FOUND);
                }
            } else {
                String redirectUrl = PASSWORD_CHANGE_URL
                        + AMPERSAND + TOKEN + EQUALS_SIGN + token
                        + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during password change command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
