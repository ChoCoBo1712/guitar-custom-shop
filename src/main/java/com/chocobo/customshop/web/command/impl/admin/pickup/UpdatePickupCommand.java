package com.chocobo.customshop.web.command.impl.admin.pickup;

import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.PagePath.EQUALS_SIGN;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;
import static com.chocobo.customshop.web.command.RequestAttribute.NAME;
import static com.chocobo.customshop.web.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdatePickupCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final PickupService pickupService = PickupServiceImpl.getInstance();
    private final Validator<String> nameValidator = NameValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Pickup> optionalPickup = pickupService.findById(entityId);
            if (optionalPickup.isPresent()) {
                Pickup pickup = optionalPickup.get();
                String previousName = pickup.getName();

                boolean valid = StringUtils.equals(name, previousName) || nameValidator.validate(name);

                if (valid) {
                    Pickup updatedPickup = Pickup.builder().of(pickup)
                            .setName(name)
                            .build();
                    pickupService.update(updatedPickup);
                    return CommandResult.createRedirectResult(ADMIN_PICKUPS_URL);
                } else {
                    session.setAttribute(VALIDATION_ERROR, true);
                    String currentEditPageUrl = ADMIN_EDIT_PICKUP_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    return CommandResult.createRedirectResult(currentEditPageUrl);
                }
            } else {
                logger.error("Requested pickup not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update pickup command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
