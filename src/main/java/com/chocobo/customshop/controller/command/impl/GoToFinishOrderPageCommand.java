package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.controller.command.PagePath.FINISH_ORDER_JSP;
import static com.chocobo.customshop.controller.command.RequestAttribute.ENTITY_ID;

public class GoToFinishOrderPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));
        request.setAttribute(ENTITY_ID, entityId);

        return new CommandResult(FINISH_ORDER_JSP, FORWARD);
    }
}
