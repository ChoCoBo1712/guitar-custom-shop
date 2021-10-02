package com.chocobo.customshop.web.command.impl.admin.user;

import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_CREATE_USER_URL;
import static com.chocobo.customshop.web.command.PagePath.ADMIN_USERS_URL;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class CreateUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final Validator<String> emailValidator = EmailValidator.getInstance();
    private final Validator<String> loginValidator = LoginValidator.getInstance();
    private final Validator<String> passwordValidator = PasswordValidator.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserRole role = UserRole.valueOf(request.getParameter(ROLE));
        UserStatus status = UserStatus.valueOf(request.getParameter(STATUS));

        try {
            boolean valid = emailValidator.validate(email) 
                    && loginValidator.validate(login) 
                    && passwordValidator.validate(password);

            if (valid) {
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
                    return CommandResult.createRedirectResult(ADMIN_CREATE_USER_URL);
                }

                userService.register(email, login, password, role, status);
                return CommandResult.createRedirectResult(ADMIN_USERS_URL);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                return CommandResult.createRedirectResult(ADMIN_CREATE_USER_URL);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during create user command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
