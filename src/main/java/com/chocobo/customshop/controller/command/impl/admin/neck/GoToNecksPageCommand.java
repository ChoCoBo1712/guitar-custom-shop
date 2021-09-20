package com.chocobo.customshop.controller.command.impl.admin.neck;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_NECKS_JSP;

public class GoToNecksPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(ADMIN_NECKS_JSP, FORWARD);
    }
}
