package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.web.command.AppRole;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.model.entity.User.UserRole;
import static com.chocobo.customshop.model.entity.User.UserStatus;
import static com.chocobo.customshop.web.command.AppRole.NOT_CONFIRMED;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.USER_ID;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        try {
            Optional<User> optionalUser = userService.login(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER_ID, user.getEntityId());
                session.setAttribute(USER_LOGIN, user.getLogin());
                session.setAttribute(USER_EMAIL, user.getEmail());

                UserRole role = user.getRole();
                UserStatus status = user.getStatus();
                switch (status) {
                    case NOT_CONFIRMED -> {
                        session.setAttribute(USER_ROLE, NOT_CONFIRMED);
                        return CommandResult.createRedirectResult(INDEX_URL);
                    }
                    case CONFIRMED -> {
                        session.setAttribute(USER_ROLE, AppRole.valueOf(role.toString()));
                        return CommandResult.createRedirectResult(INDEX_URL);
                    }
                    case DELETED -> {
                        String redirectUrl = LOGIN_URL
                                + AMPERSAND + LOGIN_ERROR + EQUALS_SIGN + true;
                        return CommandResult.createRedirectResult(redirectUrl);
                    }
                    default -> {
                        logger.error("Invalid user status: " + status);
                        return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
                    }
                }
            } else {
                String redirectUrl = LOGIN_URL
                        + AMPERSAND + LOGIN_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during login command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}