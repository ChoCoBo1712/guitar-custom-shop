package com.chocobo.customshop.controller.command.impl.admin.user;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
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
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_CREATE_USER_URL;
import static com.chocobo.customshop.controller.command.PagePath.ADMIN_USERS_URL;
import static com.chocobo.customshop.controller.command.RequestParameter.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserRole role = UserRole.valueOf(request.getParameter(ROLE));
        UserStatus status = UserStatus.valueOf(request.getParameter(STATUS));

        CommandResult result;
        try {
            ValidationUtil validationUtil = ValidationUtilImpl.getInstance();
            Pair<Boolean, List<String>> validationResult = validationUtil.validateUserCreation(email, login, password);
            if (validationResult.getLeft()) {
                UserServiceImpl.getInstance().register(email, login, password, role, status);
                result = new CommandResult(ADMIN_USERS_URL, REDIRECT);
            } else {
                List<String> errorAttributesList = validationResult.getRight();
                errorAttributesList.forEach(errorAttribute -> session.setAttribute(errorAttribute, true));
                result = new CommandResult(ADMIN_CREATE_USER_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create user command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
