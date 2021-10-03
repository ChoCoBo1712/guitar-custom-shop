package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.web.command.RequestAttribute;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.model.entity.Guitar.OrderStatus;
import static com.chocobo.customshop.util.impl.ImageUploadUtilImpl.DEFAULT_IMAGE_NAME;
import static com.chocobo.customshop.util.impl.ImageUploadUtilImpl.IMAGES_URL;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class ConstructGuitarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String name = request.getParameter(NAME);
        long bodyId = Long.parseLong(request.getParameter(BODY_ID));
        long neckId = Long.parseLong(request.getParameter(RequestAttribute.NECK_ID));
        long pickupId = Long.parseLong(request.getParameter(PICKUP_ID));
        long userId = Long.parseLong(request.getSession().getAttribute(USER_ID).toString());
        String color = request.getParameter(COLOR);
        NeckJoint neckJoint = NeckJoint.valueOf(request.getParameter(NECK_JOINT));

        try {
            boolean valid = nameValidator.validate(name)
                    && nameValidator.validate(color);

            if (valid) {
                String defaultPicturePath = IMAGES_URL + DEFAULT_IMAGE_NAME;
                GuitarServiceImpl.getInstance().insert(name, defaultPicturePath, bodyId, neckId, pickupId,
                        userId, color, neckJoint, OrderStatus.ORDERED);
                return CommandResult.createRedirectResult(INDEX_URL);
            } else {
                String redirectUrl = CONSTRUCT_GUITAR_URL
                        + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during construct guitar command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
