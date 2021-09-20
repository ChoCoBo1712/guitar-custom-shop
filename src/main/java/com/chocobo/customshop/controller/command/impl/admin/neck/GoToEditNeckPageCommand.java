package com.chocobo.customshop.controller.command.impl.admin.neck;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.FORWARD;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_EDIT_BODY_JSP;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_EDIT_NECK_JSP;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GoToEditNeckPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        NeckService neckService = NeckServiceImpl.getInstance();
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<Neck> optionalNeck = neckService.findById(entityId);
            if (optionalNeck.isPresent()) {
                Neck neck = optionalNeck.get();
                request.setAttribute(NECK, neck);
                result = new CommandResult(ADMIN_EDIT_NECK_JSP, FORWARD);
            } else {
                logger.error("Requested neck not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during go to edit neck page command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
