package com.chocobo.customshop.controller.command.impl.admin.body;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import com.chocobo.customshop.util.ValidationUtil;
import com.chocobo.customshop.util.impl.ValidationUtilImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.PagePath.EQUALS_SIGN;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateBodyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        BodyService bodyService = BodyServiceImpl.getInstance();
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<Body> optionalBody = bodyService.findById(entityId);
            if (optionalBody.isPresent()) {
                Body body = optionalBody.get();
                String previousName = body.getName();
                ValidationUtil validationUtil = ValidationUtilImpl.getInstance();
                Pair<Boolean, List<String>> validationResult = validationUtil.validateNameUpdate(name, previousName);
                if (validationResult.getLeft()) {
                    Body updatedBody = Body.builder().of(body)
                            .setName(name)
                            .setWoodId(woodId)
                            .build();
                    bodyService.update(updatedBody);
                    result = new CommandResult(ADMIN_BODIES_URL, REDIRECT);
                } else {
                    List<String> errorAttributesList = validationResult.getRight();
                    errorAttributesList.forEach(errorAttribute -> session.setAttribute(errorAttribute, true));
                    String currentEditPageUrl = ADMIN_EDIT_BODY_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    result = new CommandResult(currentEditPageUrl, REDIRECT);
                }
            } else {
                logger.error("Requested body not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update body command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
