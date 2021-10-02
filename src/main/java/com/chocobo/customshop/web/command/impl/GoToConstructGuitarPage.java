package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.web.command.PagePath.CONSTRUCT_GUITAR_JSP;

public class GoToConstructGuitarPage implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return CommandResult.createForwardResult(CONSTRUCT_GUITAR_JSP);
    }
}
