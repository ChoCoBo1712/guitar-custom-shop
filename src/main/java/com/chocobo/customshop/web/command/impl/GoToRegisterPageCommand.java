package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.web.command.PagePath.REGISTER_JSP;

public class GoToRegisterPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return CommandResult.createForwardResult(REGISTER_JSP);
    }
}