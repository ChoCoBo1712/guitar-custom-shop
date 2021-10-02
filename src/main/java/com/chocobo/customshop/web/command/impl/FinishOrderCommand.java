package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.util.ImageUploadUtil;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.validator.impl.ImagePartValidator;
import com.chocobo.customshop.util.impl.ImageUploadUtilImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;
import static com.chocobo.customshop.web.command.RequestAttribute.PICTURE_PATH;
import static com.chocobo.customshop.web.command.SessionAttribute.VALIDATION_ERROR;
import static com.chocobo.customshop.model.entity.Guitar.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class FinishOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final GuitarService guitarService = GuitarServiceImpl.getInstance();
    private final ImageUploadUtil imageUploadUtil = ImageUploadUtilImpl.getInstance();
    private final Validator<Part> imagePartValidator = ImagePartValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Guitar> optionalGuitar = guitarService.findById(entityId);
            if (optionalGuitar.isPresent()) {
                Part part = request.getPart(PICTURE_PATH);
                boolean valid = imagePartValidator.validate(part);

                if (valid) {
                    String picturePath = imageUploadUtil.uploadImage(part);

                    Guitar guitar = optionalGuitar.get();
                    Guitar updatedGuitar = builder().of(guitar)
                            .setPicturePath(picturePath)
                            .setOrderStatus(OrderStatus.COMPLETED)
                            .build();
                    guitarService.update(updatedGuitar);
                    return CommandResult.createRedirectResult(GUITAR_ORDERS_URL);
                } else {
                    request.getSession().setAttribute(VALIDATION_ERROR, true);
                    String currentPageUrl = FINISH_ORDER_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    return CommandResult.createRedirectResult(currentPageUrl);
                }
            } else {
                logger.error("Requested guitar not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during finish order command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        } catch (ServletException | IOException e) {
            logger.error("An error occurred during uploading file", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
