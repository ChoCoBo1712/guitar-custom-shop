package com.chocobo.customshop.controller.command.impl.admin.body;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.BodyService;
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

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.NAME;
import static com.chocobo.customshop.controller.command.RequestAttribute.WOOD_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateBodyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));

        CommandResult result;
        try {
            ValidationUtil validationUtil = ValidationUtilImpl.getInstance();
            Pair<Boolean, List<String>> validationResult = validationUtil.validateName(name);
            if (validationResult.getLeft()) {
                BodyServiceImpl.getInstance().insert(name, woodId);
                result = new CommandResult(ADMIN_BODIES_URL, REDIRECT);
            } else {
                List<String> errorAttributesList = validationResult.getRight();
                errorAttributesList.forEach(errorAttribute -> session.setAttribute(errorAttribute, true));
                result = new CommandResult(ADMIN_CREATE_BODY_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create body command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}