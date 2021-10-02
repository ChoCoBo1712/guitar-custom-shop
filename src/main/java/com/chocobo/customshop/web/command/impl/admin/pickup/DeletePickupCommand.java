package com.chocobo.customshop.web.command.impl.admin.pickup;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_PICKUPS_URL;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class DeletePickupCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final PickupService pickupService = PickupServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            pickupService.delete(entityId);
            return CommandResult.createRedirectResult(ADMIN_PICKUPS_URL);
        } catch (ServiceException e) {
            logger.error("An error occurred during delete pickup command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
