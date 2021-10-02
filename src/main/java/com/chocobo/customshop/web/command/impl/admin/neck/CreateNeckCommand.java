package com.chocobo.customshop.web.command.impl.admin.neck;

import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateNeckCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final NeckService neckService = NeckServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));
        long fretboardWoodId = Long.parseLong(request.getParameter(FRETBOARD_WOOD_ID));

        try {
            boolean valid = nameValidator.validate(name);

            if (valid) {
                neckService.insert(name, woodId, fretboardWoodId);
                return CommandResult.createRedirectResult(ADMIN_NECKS_URL);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                return CommandResult.createRedirectResult(ADMIN_CREATE_NECK_URL);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create neck command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
