package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.INDEX_URL;
import static com.chocobo.customshop.controller.command.PagePath.LOGIN_URL;
import static com.chocobo.customshop.controller.command.RequestParameter.LOGIN;
import static com.chocobo.customshop.controller.command.RequestParameter.PASSWORD;
import static com.chocobo.customshop.controller.command.SessionAttribute.LOGIN_ERROR;
import static com.chocobo.customshop.controller.command.SessionAttribute.USER_ROLE;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class LoginCommand implements Command {

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
                session.setAttribute(USER_ROLE, user.getRole());
                result = new CommandResult(INDEX_URL, REDIRECT);
            } else {
                request.getSession().setAttribute(LOGIN_ERROR, true);
                result = new CommandResult(LOGIN_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}