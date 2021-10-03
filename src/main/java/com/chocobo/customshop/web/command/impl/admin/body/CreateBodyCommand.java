package com.chocobo.customshop.web.command.impl.admin.body;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateBodyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final BodyService bodyService = BodyServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));

        try {
            boolean valid = nameValidator.validate(name);

            if (valid) {
                bodyService.insert(name, woodId);
                return CommandResult.createRedirectResult(ADMIN_BODIES_URL);
            } else {
                String redirectUrl = ADMIN_CREATE_BODY_URL
                        + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create body command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}