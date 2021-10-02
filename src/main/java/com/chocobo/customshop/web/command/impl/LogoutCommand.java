package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.web.command.AppRole;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.INDEX_URL;
import static com.chocobo.customshop.web.command.SessionAttribute.*;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_ID);
        request.getSession().removeAttribute(USER_LOGIN);
        request.getSession().removeAttribute(USER_EMAIL);
        request.getSession().setAttribute(USER_ROLE, AppRole.GUEST);
        return CommandResult.createRedirectResult(INDEX_URL);
    }
}
