package com.chocobo.customshop.controller.command.impl.admin.wood;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_USERS_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class DeleteWoodCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        WoodService woodService = WoodServiceImpl.getInstance();
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));
        try {
            woodService.delete(entityId);
        } catch (ServiceException e) {
            logger.error("An error occurred during delete wood command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }

        return new CommandResult(ADMIN_USERS_URL, REDIRECT);
    }
}