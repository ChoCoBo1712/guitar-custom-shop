package com.chocobo.customshop.controller.command.impl.admin.user;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.USER;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        UserRole role = UserRole.valueOf(request.getParameter(ROLE));
        UserStatus status = UserStatus.valueOf(request.getParameter(STATUS));
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<User> optionalUser = userService.findById(entityId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String previousEmail = user.getEmail();
                String previousLogin = user.getLogin();

                boolean emailsMatch = StringUtils.equals(email, previousEmail);
                boolean loginsMatch = StringUtils.equals(login, previousLogin);

                boolean valid = emailsMatch || NameValidator.getInstance().validate(email);
                valid &= loginsMatch || NameValidator.getInstance().validate(login);

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
                        return new CommandResult(ADMIN_CREATE_USER_URL, REDIRECT);
                    }

                    User updatedUser = User.builder().of(user)
                            .setEmail(email)
                            .setLogin(login)
                            .setRole(role)
                            .setStatus(status)
                            .build();
                    userService.update(updatedUser);

                    result = new CommandResult(ADMIN_USERS_URL, REDIRECT);
                } else {
                    session.setAttribute(VALIDATION_ERROR, true);
                    String currentEditPageUrl = ADMIN_EDIT_USER_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    result = new CommandResult(currentEditPageUrl, REDIRECT);
                }
            } else {
                logger.error("Requested user not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update user command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
