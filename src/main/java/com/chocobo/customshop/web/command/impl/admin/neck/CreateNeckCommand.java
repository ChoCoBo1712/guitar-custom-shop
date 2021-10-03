package com.chocobo.customshop.web.command.impl.admin.neck;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
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

public class CreateNeckCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final NeckService neckService = NeckServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));
        long fretboardWoodId = Long.parseLong(request.getParameter(FRETBOARD_WOOD_ID));

        try {
            boolean valid = nameValidator.validate(name);

            if (valid) {
                neckService.insert(name, woodId, fretboardWoodId);
                return CommandResult.createRedirectResult(ADMIN_NECKS_URL);
            } else {
                String redirectUrl = ADMIN_CREATE_NECK_URL
                        + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create neck command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
