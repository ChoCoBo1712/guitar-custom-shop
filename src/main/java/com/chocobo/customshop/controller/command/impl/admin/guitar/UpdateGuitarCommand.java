package com.chocobo.customshop.controller.command.impl.admin.guitar;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.RequestAttribute;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.model.validator.impl.ImagePartValidator;
import com.chocobo.customshop.util.impl.ImageUploadUtilImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateGuitarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        GuitarService guitarService = GuitarServiceImpl.getInstance();
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long bodyId = Long.parseLong(request.getParameter(BODY_ID));
        long neckId = Long.parseLong(request.getParameter(RequestAttribute.NECK_ID));
        long pickupId = Long.parseLong(request.getParameter(PICKUP_ID));
        long userId = Long.parseLong(request.getParameter(USER_ID));
        String color = request.getParameter(COLOR);
        NeckJoint neckJoint = NeckJoint.valueOf(request.getParameter(NECK_JOINT));
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<Guitar> optionalGuitar = guitarService.findById(entityId);
            if (optionalGuitar.isPresent()) {
                Part part = request.getPart(PICTURE_PATH);
                Guitar guitar = optionalGuitar.get();
                String previousName = guitar.getName();
                String previousColor = guitar.getColor();

                boolean valid = StringUtils.equals(name, previousName) || NameValidator.getInstance().validate(name);
                valid &= StringUtils.equals(color, previousColor) || NameValidator.getInstance().validate(color);
                valid &= ImagePartValidator.getInstance().validate(part);

                if (valid) {
                    String picturePath = ImageUploadUtilImpl.getInstance().uploadImage(part);
                    Guitar updatedGuitar = Guitar.builder().of(guitar)
                            .setName(name)
                            .setBodyId(bodyId)
                            .setNeckId(neckId)
                            .setPickupId(pickupId)
                            .setUserId(userId)
                            .setColor(color)
                            .setNeckJoint(neckJoint)
                            .setPicturePath(picturePath)
                            .build();
                    guitarService.update(updatedGuitar);
                    result = new CommandResult(ADMIN_GUITARS_URL, REDIRECT);
                } else {
                    session.setAttribute(VALIDATION_ERROR, true);
                    String currentEditPageUrl = ADMIN_EDIT_GUITAR_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    result = new CommandResult(currentEditPageUrl, REDIRECT);
                }
            } else {
                logger.error("Requested guitar not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update guitar command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        } catch (ServletException | IOException e) {
            logger.error("An error occurred during uploading file", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
