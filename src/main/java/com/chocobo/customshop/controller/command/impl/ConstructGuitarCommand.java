package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.RequestAttribute;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.VALIDATION_ERROR;
import static com.chocobo.customshop.model.entity.Guitar.*;
import static com.chocobo.customshop.util.impl.ImageUploadUtilImpl.DEFAULT_IMAGE_NAME;
import static com.chocobo.customshop.util.impl.ImageUploadUtilImpl.IMAGES_URL;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class ConstructGuitarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long bodyId = Long.parseLong(request.getParameter(BODY_ID));
        long neckId = Long.parseLong(request.getParameter(RequestAttribute.NECK_ID));
        long pickupId = Long.parseLong(request.getParameter(PICKUP_ID));
        long userId = ((User) session.getAttribute(USER)).getEntityId();
        String color = request.getParameter(COLOR);
        NeckJoint neckJoint = NeckJoint.valueOf(request.getParameter(NECK_JOINT));

        CommandResult result;
        try {
            Validator<String> nameValidator = NameValidator.getInstance();

            boolean valid = nameValidator.validate(name);
            valid &= nameValidator.validate(color);

            if (valid) {
                String defaultPicturePath = IMAGES_URL + DEFAULT_IMAGE_NAME;
                GuitarServiceImpl.getInstance().insert(name, defaultPicturePath, bodyId, neckId, pickupId,
                        userId, color, neckJoint, OrderStatus.ORDERED);
                result = new CommandResult(INDEX_URL, REDIRECT);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                result = new CommandResult(CONSTRUCT_GUITAR_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during construct guitar command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
