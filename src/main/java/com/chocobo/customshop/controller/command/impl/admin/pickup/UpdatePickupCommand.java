package com.chocobo.customshop.controller.command.impl.admin.pickup;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.PagePath.EQUALS_SIGN;
import static com.chocobo.customshop.controller.command.RequestAttribute.ENTITY_ID;
import static com.chocobo.customshop.controller.command.RequestAttribute.NAME;
import static com.chocobo.customshop.controller.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdatePickupCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        PickupService pickupService = PickupServiceImpl.getInstance();
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<Pickup> optionalPickup = pickupService.findById(entityId);
            if (optionalPickup.isPresent()) {
                Pickup pickup = optionalPickup.get();
                String previousName = pickup.getName();

                boolean valid = StringUtils.equals(name, previousName) || NameValidator.getInstance().validate(name);

                if (valid) {
                    Pickup updatedPickup = Pickup.builder().of(pickup)
                            .setName(name)
                            .build();
                    pickupService.update(updatedPickup);
                    result = new CommandResult(ADMIN_PICKUPS_URL, REDIRECT);
                } else {
                    session.setAttribute(VALIDATION_ERROR, true);
                    String currentEditPageUrl = ADMIN_EDIT_PICKUP_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    result = new CommandResult(currentEditPageUrl, REDIRECT);
                }
            } else {
                logger.error("Requested pickup not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update pickup command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
