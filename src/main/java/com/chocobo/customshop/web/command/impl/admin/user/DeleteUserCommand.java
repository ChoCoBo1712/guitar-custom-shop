package com.chocobo.customshop.web.command.impl.admin.user;

import com.chocobo.customshop.web.command.AppRole;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.web.listener.HttpSessionAttributeListenerImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_USERS_URL;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.USER_ROLE;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class DeleteUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            userService.delete(entityId);

            Optional<HttpSession> optionalSession = HttpSessionAttributeListenerImpl.findSession(entityId);
            if (optionalSession.isPresent()) {
                HttpSession session = optionalSession.get();
                session.removeAttribute(USER_ID);
                session.removeAttribute(USER_LOGIN);
                session.removeAttribute(USER_EMAIL);
                session.setAttribute(USER_ROLE, AppRole.GUEST);
            }

            return CommandResult.createRedirectResult(ADMIN_USERS_URL);
        } catch (ServiceException e) {
            logger.error("An error occurred during delete user command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
