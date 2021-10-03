package com.chocobo.customshop.web.command.impl.admin.guitar;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.ImagePartValidator;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.util.ImageUploadUtil;
import com.chocobo.customshop.util.impl.ImageUploadUtilImpl;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.web.command.RequestAttribute;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static com.chocobo.customshop.model.entity.Guitar.OrderStatus;
import static com.chocobo.customshop.model.entity.Guitar.builder;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateGuitarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final GuitarService guitarService = GuitarServiceImpl.getInstance();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final ImageUploadUtil imageUploadUtil = ImageUploadUtilImpl.getInstance();
    private final Validator<Part> imagePartValidator = ImagePartValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String name = request.getParameter(NAME);
        long bodyId = Long.parseLong(request.getParameter(BODY_ID));
        long neckId = Long.parseLong(request.getParameter(RequestAttribute.NECK_ID));
        long pickupId = Long.parseLong(request.getParameter(PICKUP_ID));
        long userId = Long.parseLong(request.getParameter(USER_ID));
        String color = request.getParameter(COLOR);
        NeckJoint neckJoint = NeckJoint.valueOf(request.getParameter(NECK_JOINT));
        OrderStatus orderStatus = OrderStatus.valueOf(request.getParameter(ORDER_STATUS));
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Guitar> optionalGuitar = guitarService.findById(entityId);
            if (optionalGuitar.isPresent()) {
                Part part = request.getPart(PICTURE_PATH);
                Guitar guitar = optionalGuitar.get();
                String previousName = guitar.getName();
                String previousColor = guitar.getColor();

                boolean valid = StringUtils.equals(name, previousName) || nameValidator.validate(name)
                        && StringUtils.equals(color, previousColor) || nameValidator.validate(color)
                        && imagePartValidator.validate(part);

                if (valid) {
                    String picturePath = imageUploadUtil.uploadImage(part);
                    Guitar updatedGuitar;
                    if (imageUploadUtil.isDefaultPicturePath(picturePath)) {
                        updatedGuitar = builder().of(guitar)
                                .setName(name)
                                .setBodyId(bodyId)
                                .setNeckId(neckId)
                                .setPickupId(pickupId)
                                .setUserId(userId)
                                .setColor(color)
                                .setNeckJoint(neckJoint)
                                .setOrderStatus(orderStatus)
                                .build();
                    } else {
                        updatedGuitar = builder().of(guitar)
                                .setName(name)
                                .setBodyId(bodyId)
                                .setNeckId(neckId)
                                .setPickupId(pickupId)
                                .setUserId(userId)
                                .setColor(color)
                                .setNeckJoint(neckJoint)
                                .setOrderStatus(orderStatus)
                                .setPicturePath(picturePath)
                                .build();
                    }
                    guitarService.update(updatedGuitar);
                    return CommandResult.createRedirectResult(ADMIN_GUITARS_URL);
                } else {
                    String redirectUrl = ADMIN_EDIT_GUITAR_URL
                            + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId
                            + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                    return CommandResult.createRedirectResult(redirectUrl);
                }
            } else {
                logger.error("Requested guitar not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update guitar command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        } catch (ServletException | IOException e) {
            logger.error("An error occurred during uploading file", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
