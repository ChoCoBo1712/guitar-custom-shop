package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.controller.command.PagePath.GUITAR_ORDERS_JSP;

public class GoToGuitarOrdersPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(GUITAR_ORDERS_JSP, FORWARD);
    }
}
