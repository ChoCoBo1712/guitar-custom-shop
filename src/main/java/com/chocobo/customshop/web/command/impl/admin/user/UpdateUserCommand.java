package com.chocobo.customshop.web.command.impl.admin.user;

import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.EmailValidator;
import com.chocobo.customshop.model.validator.impl.LoginValidator;
import com.chocobo.customshop.model.validator.impl.PasswordValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();
    private final Validator<String> emailValidator = EmailValidator.getInstance();
    private final Validator<String> loginValidator = LoginValidator.getInstance();
    private final Validator<String> passwordValidator = PasswordValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserRole role = UserRole.valueOf(request.getParameter(ROLE));
        UserStatus status = UserStatus.valueOf(request.getParameter(STATUS));
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<User> optionalUser = userService.findById(entityId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String previousEmail = user.getEmail();
                String previousLogin = user.getLogin();

                boolean emailsMatch = StringUtils.equals(email, previousEmail);
                boolean loginsMatch = StringUtils.equals(login, previousLogin);
                boolean passwordIsEmpty = StringUtils.isEmpty(password);

                boolean valid = emailsMatch || emailValidator.validate(email) 
                        && loginsMatch || loginValidator.validate(login) 
                        && passwordIsEmpty || passwordValidator.validate(password);

                if (valid) {
                    boolean duplicate = false;

                    if (!emailsMatch && !userService.isEmailUnique(email)) {
                        duplicate = true;
                        session.setAttribute(DUPLICATE_EMAIL_ERROR, true);
                    }
                    if (!loginsMatch && !userService.isLoginUnique(login)) {
                        duplicate = true;
                        session.setAttribute(DUPLICATE_LOGIN_ERROR, true);
                    }
                    if (duplicate) {
                        return CommandResult.createRedirectResult(ADMIN_CREATE_USER_URL);
                    }

                    User updatedUser = User.builder().of(user)
                            .setEmail(email)
                            .setLogin(login)
                            .setRole(role)
                            .setStatus(status)
                            .build();
                    if (passwordIsEmpty) {
                        userService.update(updatedUser);
                    } else {
                        userService.updateWithPassword(updatedUser, password);
                    }

                    return CommandResult.createRedirectResult(ADMIN_USERS_URL);
                } else {
                    session.setAttribute(VALIDATION_ERROR, true);
                    String currentEditPageUrl = ADMIN_EDIT_USER_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    return CommandResult.createRedirectResult(currentEditPageUrl);
                }
            } else {
                logger.error("Requested user not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update user command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
