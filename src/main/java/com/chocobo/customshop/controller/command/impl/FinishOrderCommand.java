package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
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

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.ENTITY_ID;
import static com.chocobo.customshop.controller.command.RequestAttribute.PICTURE_PATH;
import static com.chocobo.customshop.controller.command.SessionAttribute.VALIDATION_ERROR;
import static com.chocobo.customshop.model.entity.Guitar.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class FinishOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        GuitarService guitarService = GuitarServiceImpl.getInstance();
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<Guitar> optionalGuitar = guitarService.findById(entityId);
            if (optionalGuitar.isPresent()) {
                Part part = request.getPart(PICTURE_PATH);
                boolean valid = ImagePartValidator.getInstance().validate(part);

                if (valid) {
                    String picturePath = ImageUploadUtilImpl.getInstance().uploadImage(part);

                    Guitar guitar = optionalGuitar.get();
                    Guitar updatedGuitar = builder().of(guitar)
                            .setPicturePath(picturePath)
                            .setOrderStatus(OrderStatus.COMPLETED)
                            .build();
                    guitarService.update(updatedGuitar);
                    result = new CommandResult(GUITAR_ORDERS_URL, REDIRECT);
                } else {
                    request.getSession().setAttribute(VALIDATION_ERROR, true);
                    String currentPageUrl = FINISH_ORDER_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    result = new CommandResult(currentPageUrl, REDIRECT);
                }
            } else {
                logger.error("Requested guitar not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during finish order command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        } catch (ServletException | IOException e) {
            logger.error("An error occurred during uploading file", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
