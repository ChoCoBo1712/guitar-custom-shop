package com.chocobo.customshop.web.command.impl.admin.pickup;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_EDIT_PICKUP_JSP;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GoToEditPickupPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final PickupService pickupService = PickupServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Pickup> optionalPickup = pickupService.findById(entityId);
            if (optionalPickup.isPresent()) {
                Pickup pickup = optionalPickup.get();
                request.setAttribute(PICKUP, pickup);
                return CommandResult.createForwardResult(ADMIN_EDIT_PICKUP_JSP);
            } else {
                logger.error("Requested pickup not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during go to edit pickup page command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
