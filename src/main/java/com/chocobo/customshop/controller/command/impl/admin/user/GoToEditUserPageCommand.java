package com.chocobo.customshop.controller.command.impl.admin.user;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_EDIT_USER_JSP;
import static com.chocobo.customshop.controller.command.PagePath.LOGIN_URL;
import static com.chocobo.customshop.controller.command.RequestParameter.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.USER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GoToEditUserPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));
        try {
            Optional<User> optionalUser = userService.findById(entityId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                request.setAttribute(USER, user);
            } else {
                logger.error("Requested user not found, id = " + entityId);
                return new CommandResult(SC_NOT_FOUND, ERROR);
                // TODO: 12.09.2021 ask about not found 
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during go to edit user page command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }

        return new CommandResult(ADMIN_EDIT_USER_JSP, FORWARD);
    }
}