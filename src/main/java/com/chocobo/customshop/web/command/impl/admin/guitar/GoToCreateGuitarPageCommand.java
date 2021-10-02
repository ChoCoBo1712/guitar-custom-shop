package com.chocobo.customshop.web.command.impl.admin.guitar;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_CREATE_GUITAR_JSP;

public class GoToCreateGuitarPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return CommandResult.createForwardResult(ADMIN_CREATE_GUITAR_JSP);
    }
}
