package com.chocobo.customshop.controller.command.impl.admin.pickup;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_EDIT_PICKUP_JSP;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_EDIT_WOOD_JSP;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GoToEditPickupPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        PickupService pickupService = PickupServiceImpl.getInstance();
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<Pickup> optionalPickup = pickupService.findById(entityId);
            if (optionalPickup.isPresent()) {
                Pickup pickup = optionalPickup.get();
                request.setAttribute(PICKUP, pickup);
                result = new CommandResult(ADMIN_EDIT_PICKUP_JSP, FORWARD);
            } else {
                logger.error("Requested pickup not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during go to edit pickup page command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
