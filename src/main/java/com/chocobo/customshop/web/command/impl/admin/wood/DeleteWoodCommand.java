package com.chocobo.customshop.web.command.impl.admin.wood;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_WOODS_URL;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class DeleteWoodCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final WoodService woodService = WoodServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            woodService.delete(entityId);
            return CommandResult.createRedirectResult(ADMIN_WOODS_URL);
        } catch (ServiceException e) {
            logger.error("An error occurred during delete wood command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
