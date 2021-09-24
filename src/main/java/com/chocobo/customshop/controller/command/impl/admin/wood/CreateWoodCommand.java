package com.chocobo.customshop.controller.command.impl.admin.wood;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_CREATE_WOOD_URL;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_WOODS_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.NAME;
import static com.chocobo.customshop.controller.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateWoodCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);

        CommandResult result;
        try {
            boolean valid = NameValidator.getInstance().validate(name);

            if (valid) {
                WoodServiceImpl.getInstance().insert(name);
                result = new CommandResult(ADMIN_WOODS_URL, REDIRECT);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                result = new CommandResult(ADMIN_CREATE_WOOD_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create wood command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
