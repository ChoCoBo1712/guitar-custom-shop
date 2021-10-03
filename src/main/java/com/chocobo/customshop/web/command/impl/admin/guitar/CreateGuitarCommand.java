package com.chocobo.customshop.web.command.impl.admin.guitar;

import com.chocobo.customshop.exception.ServiceException;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.chocobo.customshop.model.entity.Guitar.OrderStatus;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateGuitarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> nameValidator = NameValidator.getInstance();
    private final Validator<Part> imagePartValidator = ImagePartValidator.getInstance();
    private final ImageUploadUtil imageUploadUtil = ImageUploadUtilImpl.getInstance();
    private final GuitarService guitarService = GuitarServiceImpl.getInstance();

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

        try {
            Part part = request.getPart(PICTURE_PATH);

            boolean valid = nameValidator.validate(name)
                    && nameValidator.validate(color)
                    && imagePartValidator.validate(part);

            if (valid) {
                String picturePath = imageUploadUtil.uploadImage(part);

                guitarService.insert(name, picturePath, bodyId, neckId, pickupId, userId, color, neckJoint, orderStatus);
                return CommandResult.createRedirectResult(ADMIN_GUITARS_URL);
            } else {
                String redirectUrl = ADMIN_CREATE_GUITAR_URL
                        + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create guitar command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        } catch (ServletException | IOException e) {
            logger.error("An error occurred during uploading file", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
