package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.controller.command.PagePath.REGISTER_SUCCESS_JSP;

public class GoToRegisterSuccessPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(REGISTER_SUCCESS_JSP, FORWARD);
    }
}