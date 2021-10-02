package com.chocobo.customshop.web.command.impl.admin.pickup;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.web.command.PagePath.*;

public class GoToCreatePickupPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return CommandResult.createForwardResult(ADMIN_CREATE_PICKUP_JSP);
    }
}
