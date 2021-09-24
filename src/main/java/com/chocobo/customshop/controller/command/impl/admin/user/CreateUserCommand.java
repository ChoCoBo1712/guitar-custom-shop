package com.chocobo.customshop.controller.command.impl.admin.user;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.*;
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
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.*;
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
            boolean valid = EmailValidator.getInstance().validate(email);
            valid &= LoginValidator.getInstance().validate(login);
            valid &= PasswordValidator.getInstance().validate(password);

            if (valid) {
                UserService userService = UserServiceImpl.getInstance();
                boolean duplicate = false;

                if (!userService.isEmailUnique(email)) {
                    duplicate = true;
                    session.setAttribute(DUPLICATE_EMAIL_ERROR, true);
                }
                if (!userService.isLoginUnique(login)) {
                    duplicate = true;
                    session.setAttribute(DUPLICATE_LOGIN_ERROR, true);
                }
                if (duplicate) {
                    return new CommandResult(ADMIN_CREATE_USER_URL, REDIRECT);
                }

                userService.register(email, login, password, role, status);
                result = new CommandResult(ADMIN_USERS_URL, REDIRECT);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                result = new CommandResult(ADMIN_CREATE_USER_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create user command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
