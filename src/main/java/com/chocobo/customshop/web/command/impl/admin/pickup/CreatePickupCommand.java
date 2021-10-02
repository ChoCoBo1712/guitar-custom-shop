package com.chocobo.customshop.web.command.impl.admin.pickup;

import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_CREATE_PICKUP_URL;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_PICKUPS_URL;
import static com.chocobo.customshop.web.command.RequestAttribute.NAME;
import static com.chocobo.customshop.web.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreatePickupCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final PickupService pickupService = PickupServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);

        try {
            boolean valid = nameValidator.validate(name);

            if (valid) {
                pickupService.insert(name);
                return CommandResult.createRedirectResult(ADMIN_PICKUPS_URL);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                return CommandResult.createRedirectResult(ADMIN_CREATE_PICKUP_URL);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create pickup command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
