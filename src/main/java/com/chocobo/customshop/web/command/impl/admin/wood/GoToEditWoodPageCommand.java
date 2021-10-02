package com.chocobo.customshop.web.command.impl.admin.wood;

import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_EDIT_WOOD_JSP;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;
import static com.chocobo.customshop.web.command.RequestAttribute.WOOD;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GoToEditWoodPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final WoodService woodService = WoodServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Wood> optionalWood = woodService.findById(entityId);
            if (optionalWood.isPresent()) {
                Wood wood = optionalWood.get();
                request.setAttribute(WOOD, wood);
                return CommandResult.createForwardResult(ADMIN_EDIT_WOOD_JSP);
            } else {
                logger.error("Requested wood not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during go to edit wood page command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
