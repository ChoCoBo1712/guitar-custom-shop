package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.web.command.PagePath.MY_GUITARS_JSP;

public class GoToMyGuitarsPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return CommandResult.createForwardResult(MY_GUITARS_JSP);
    }
}
