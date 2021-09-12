package com.chocobo.customshop.controller.command.impl.admin.user;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_USERS_URL;
import static com.chocobo.customshop.controller.command.RequestParameter.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class DeleteUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        UserStatus status = UserStatus.DELETED;
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));
        try {
            Optional<User> optionalUser = userService.findById(entityId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                User updatedUser = User.builder().of(user)
                        .setStatus(status)
                        .build();
                userService.update(updatedUser);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during delete user command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }

        return new CommandResult(ADMIN_USERS_URL, REDIRECT);
    }
}
