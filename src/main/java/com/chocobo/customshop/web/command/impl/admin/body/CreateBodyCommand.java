package com.chocobo.customshop.web.command.impl.admin.body;

import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_BODIES_URL;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_CREATE_BODY_URL;
import static com.chocobo.customshop.web.command.RequestAttribute.NAME;
import static com.chocobo.customshop.web.command.RequestAttribute.WOOD_ID;
import static com.chocobo.customshop.web.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateBodyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final BodyService bodyService = BodyServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));

        try {
            boolean valid = nameValidator.validate(name);

            if (valid) {
                bodyService.insert(name, woodId);
                return CommandResult.createRedirectResult(ADMIN_BODIES_URL);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                return CommandResult.createRedirectResult(ADMIN_CREATE_BODY_URL);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create body command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}