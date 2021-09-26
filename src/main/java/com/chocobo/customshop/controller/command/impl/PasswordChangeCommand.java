package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.PasswordValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.PASSWORD_CHANGE;
import static com.chocobo.customshop.controller.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class PasswordChangeCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = request.getParameter(TOKEN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        CommandResult result;
        try {
            boolean valid = PasswordValidator.getInstance().validate(password);
            if (valid) {
                UserService userService = UserServiceImpl.getInstance();
                Optional<User> optionalUser = userService.findByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    userService.updateWithPassword(user, password);

                    session.setAttribute(PASSWORD_CHANGE, true);
                    return new CommandResult(TOKEN_SUCCESS_URL, REDIRECT);
                } else {
                    logger.error("User with email given in token is not found");
                    result = new CommandResult(SC_NOT_FOUND, ERROR);
                }
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                String currentFormPageUrl = PASSWORD_CHANGE_URL + AMPERSAND + TOKEN + EQUALS_SIGN + token;
                result = new CommandResult(currentFormPageUrl, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during password change command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
