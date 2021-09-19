package com.chocobo.customshop.controller.command.impl.admin.body;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_BODIES_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class DeleteBodyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        BodyService bodyService = BodyServiceImpl.getInstance();
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            bodyService.delete(entityId);
            result = new CommandResult(ADMIN_BODIES_URL, REDIRECT);
        } catch (ServiceException e) {
            logger.error("An error occurred during delete body command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}