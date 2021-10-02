package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.web.command.PagePath.GUITAR_ORDERS_JSP;

public class GoToGuitarOrdersPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return CommandResult.createForwardResult(GUITAR_ORDERS_JSP);
    }
}
