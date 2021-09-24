package com.chocobo.customshop.controller.command.impl.admin.guitar;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.RequestAttribute;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.model.validator.impl.PartValidator;
import com.chocobo.customshop.util.impl.ImageUploadUtilImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_CREATE_GUITAR_URL;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_GUITARS_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.VALIDATION_ERROR;
import static com.chocobo.customshop.model.validator.Validator.SERVICE_EXCEPTION;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateGuitarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long bodyId = Long.parseLong(request.getParameter(BODY_ID));
        long neckId = Long.parseLong(request.getParameter(RequestAttribute.NECK_ID));
        long pickupId = Long.parseLong(request.getParameter(PICKUP_ID));
        long userId = Long.parseLong(request.getParameter(USER_ID));
        String color = request.getParameter(COLOR);
        NeckJoint neckJoint = NeckJoint.valueOf(request.getParameter(NECK_JOINT));

        CommandResult result;
        try {
            Part part = request.getPart(PICTURE_PATH);

            boolean valid = NameValidator.getInstance().validate(name);
            valid &= NameValidator.getInstance().validate(color);
            valid &= PartValidator.getInstance().validate(part);

            if (valid) {
                String picturePath = ImageUploadUtilImpl.getInstance().uploadImage(part);

                GuitarServiceImpl.getInstance().insert(name, picturePath, bodyId, neckId, pickupId, userId, color, neckJoint);
                result = new CommandResult(ADMIN_GUITARS_URL, REDIRECT);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                result = new CommandResult(ADMIN_CREATE_GUITAR_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create guitar command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        } catch (ServletException | IOException e) {
            logger.error("An error occurred during uploading file", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
