package com.chocobo.customshop.web.command.impl.admin.body;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateBodyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final BodyService bodyService = BodyServiceImpl.getInstance();
    private final Validator<String> nameValidator = NameValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Body> optionalBody = bodyService.findById(entityId);
            if (optionalBody.isPresent()) {
                Body body = optionalBody.get();
                String previousName = body.getName();

                boolean valid = StringUtils.equals(name, previousName) || nameValidator.validate(name);

                if (valid) {
                    Body updatedBody = Body.builder().of(body)
                            .setName(name)
                            .setWoodId(woodId)
                            .build();
                    bodyService.update(updatedBody);
                    return CommandResult.createRedirectResult(ADMIN_BODIES_URL);
                } else {
                    String redirectUrl = ADMIN_EDIT_BODY_URL
                            + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId
                            + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                    return CommandResult.createRedirectResult(redirectUrl);
                }
            } else {
                logger.error("Requested body not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update body command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
