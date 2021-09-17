package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.INDEX_URL;
import static com.chocobo.customshop.controller.command.PagePath.LOGIN_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.LOGIN;
import static com.chocobo.customshop.controller.command.RequestAttribute.PASSWORD;
import static com.chocobo.customshop.controller.command.SessionAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.LOGIN_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_ERROR, false);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        CommandResult result;
        try {
            Optional<User> optionalUser = UserServiceImpl.getInstance().login(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                result = new CommandResult(INDEX_URL, REDIRECT);
            } else {
                session.setAttribute(LOGIN_ERROR, true);
                result = new CommandResult(LOGIN_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during login command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}