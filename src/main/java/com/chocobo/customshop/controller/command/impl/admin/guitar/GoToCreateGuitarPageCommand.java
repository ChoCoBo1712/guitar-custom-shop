package com.chocobo.customshop.controller.command.impl.admin.guitar;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_CREATE_GUITAR_JSP;

public class GoToCreateGuitarPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(ADMIN_CREATE_GUITAR_JSP, FORWARD);
    }
}
