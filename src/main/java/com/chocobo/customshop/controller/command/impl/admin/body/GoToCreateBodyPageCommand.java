package com.chocobo.customshop.controller.command.impl.admin.body;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.controller.command.PagePath.*;

public class GoToCreateBodyPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(ADMIN_CREATE_BODY_JSP, FORWARD);
    }
}