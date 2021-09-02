package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.PagePath;
import com.chocobo.customshop.controller.command.SessionAttribute;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.service.impl.UserServiceImpl;
import jakarta.faces.component.html.HtmlOutputFormat;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestParameter.*;

public class LoginCommand implements Command {

    // TODO: 31.08.2021 translate
    private static final String ERROR_MSG = "Incorrect login or password";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        CommandResult result;
        try {
            Optional<User> optionalUser = UserServiceImpl.getInstance().login(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                request.getSession().setAttribute(SessionAttribute.USER_ROLE, user.getRole());
                result = new CommandResult(INDEX_JSP, REDIRECT);
            } else {
                request.setAttribute(LOGIN_ERROR, ERROR_MSG);
                result = new CommandResult(LOGIN_JSP, REDIRECT);
            }
        } catch (ServiceException e) {
            result = new CommandResult(ERROR_500_JSP, REDIRECT);
        }
        return result;
    }
}