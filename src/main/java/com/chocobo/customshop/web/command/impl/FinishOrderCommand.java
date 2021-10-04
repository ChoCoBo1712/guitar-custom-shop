package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.ImagePartValidator;
import com.chocobo.customshop.util.ImageUploadUtil;
import com.chocobo.customshop.util.MailUtil;
import com.chocobo.customshop.util.impl.ImageUploadUtilImpl;
import com.chocobo.customshop.util.impl.MailUtilImpl;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
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

public class FinishOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final GuitarService guitarService = GuitarServiceImpl.getInstance();
    private final ImageUploadUtil imageUploadUtil = ImageUploadUtilImpl.getInstance();
    private final Validator<Part> imagePartValidator = ImagePartValidator.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final MailUtil mailUtil = MailUtilImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Guitar> optionalGuitar = guitarService.findById(entityId);
            if (optionalGuitar.isPresent()) {
                Part part = request.getPart(PICTURE_PATH);
                boolean valid = imagePartValidator.validate(part);

                if (valid) {
                    Guitar guitar = optionalGuitar.get();
                    long userId = guitar.getUserId();
                    Optional<User> optionalUser = userService.findById(userId);
                    if (optionalUser.isPresent()) {
                        String picturePath = imageUploadUtil.uploadImage(part);

                        Guitar updatedGuitar = builder().of(guitar)
                                .setPicturePath(picturePath)
                                .setOrderStatus(OrderStatus.COMPLETED)
                                .build();
                        guitarService.update(updatedGuitar);

                        User user = optionalUser.get();
                        mailUtil.senOrderCompletedMail(user.getEmail(), guitar.getName(),
                                request.getScheme(), request.getServerName());
                        return CommandResult.createRedirectResult(GUITAR_ORDERS_URL);
                    } else {
                        logger.error("Requested user not found, id = " + entityId);
                        return CommandResult.createErrorResult(SC_NOT_FOUND);
                    }
                } else {
                    request.getSession().setAttribute(VALIDATION_ERROR, true);
                    String redirectUrl = FINISH_ORDER_URL
                            + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    return CommandResult.createRedirectResult(redirectUrl);
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
