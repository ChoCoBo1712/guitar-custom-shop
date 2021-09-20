package com.chocobo.customshop.controller.command.impl.admin.neck;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
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
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.model.entity.Neck.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateNeckCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        Tuner tuner = Tuner.valueOf(request.getParameter(TUNER));
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));
        long fretboardWoodId = Long.parseLong(request.getParameter(FRETBOARD_WOOD_ID));

        CommandResult result;
        try {
            ValidationUtil validationUtil = ValidationUtilImpl.getInstance();
            Pair<Boolean, List<String>> validationResult = validationUtil.validateName(name);
            if (validationResult.getLeft()) {
                NeckServiceImpl.getInstance().insert(name, tuner, woodId, fretboardWoodId);
                result = new CommandResult(ADMIN_NECKS_URL, REDIRECT);
            } else {
                List<String> errorAttributesList = validationResult.getRight();
                errorAttributesList.forEach(errorAttribute -> session.setAttribute(errorAttribute, true));
                result = new CommandResult(ADMIN_CREATE_NECK_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create neck command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}