package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.INDEX_URL;
import static com.chocobo.customshop.controller.command.SessionAttribute.USER;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession().removeAttribute(USER);
        return new CommandResult(INDEX_URL, REDIRECT);
    }
}
