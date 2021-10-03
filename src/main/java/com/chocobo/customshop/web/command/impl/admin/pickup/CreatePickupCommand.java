package com.chocobo.customshop.web.command.impl.admin.pickup;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.NAME;
import static com.chocobo.customshop.web.command.RequestAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreatePickupCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final PickupService pickupService = PickupServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String name = request.getParameter(NAME);

        try {
            boolean valid = nameValidator.validate(name);

            if (valid) {
                pickupService.insert(name);
                return CommandResult.createRedirectResult(ADMIN_PICKUPS_URL);
            } else {
                String redirectUrl = ADMIN_CREATE_PICKUP_URL
                        + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create pickup command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
