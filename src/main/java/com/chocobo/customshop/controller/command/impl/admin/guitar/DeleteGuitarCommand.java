package com.chocobo.customshop.controller.command.impl.admin.guitar;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_GUITARS_URL;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_NECKS_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class DeleteGuitarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        GuitarService guitarService = GuitarServiceImpl.getInstance();
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            guitarService.delete(entityId);
            result = new CommandResult(ADMIN_GUITARS_URL, REDIRECT);
        } catch (ServiceException e) {
            logger.error("An error occurred during delete guitar command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
