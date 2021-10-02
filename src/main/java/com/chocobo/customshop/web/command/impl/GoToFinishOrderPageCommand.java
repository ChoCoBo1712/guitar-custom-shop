package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.web.command.PagePath.FINISH_ORDER_JSP;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;

public class GoToFinishOrderPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));
        request.setAttribute(ENTITY_ID, entityId);

        return CommandResult.createForwardResult(FINISH_ORDER_JSP);
    }
}
