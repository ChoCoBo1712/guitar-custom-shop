package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.PagePath;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.service.impl.UserServiceImpl;
import jakarta.faces.component.html.HtmlOutputFormat;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.RequestParameter.*;

public class LoginCommand implements Command {

    private static final String ERROR_MSG = "Incorrect login or password";

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        try {
            Optional<User> user = UserServiceImpl.getInstance().login(login, password);
            if (user.isPresent()) {
                // TODO: 08.08.2021 session things
                return PagePath.INDEX_JSP;
            } else {
                request.setAttribute(LOGIN_ERROR, ERROR_MSG);
                return PagePath.LOGIN_JSP;
            }
        } catch (ServiceException e) {
            // TODO: 08.08.2021 specify error
            return PagePath.ERROR_500_JSP;
        }
    }
}