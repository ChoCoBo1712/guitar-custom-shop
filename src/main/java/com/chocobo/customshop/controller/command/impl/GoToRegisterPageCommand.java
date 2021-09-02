package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.controller.command.PagePath.*;

public class GoToRegisterPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(REGISTER_JSP, FORWARD);
    }
}