package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.EmailValidator;
import com.chocobo.customshop.model.validator.impl.LoginValidator;
import com.chocobo.customshop.model.validator.impl.PasswordValidator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.PagePath.EQUALS_SIGN;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
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

                boolean valid = emailsMatch || EmailValidator.getInstance().validate(email)
                        && loginsMatch || LoginValidator.getInstance().validate(login)
                        && passwordIsEmpty || PasswordValidator.getInstance().validate(password);

                String redirectUrl = PROFILE_URL;
                if (valid) {
                    boolean duplicate = false;

                    if (!emailsMatch && !userService.isEmailUnique(email)) {
                        duplicate = true;
                        redirectUrl += AMPERSAND + DUPLICATE_EMAIL_ERROR + true;
                    }
                    if (!loginsMatch && !userService.isLoginUnique(login)) {
                        duplicate = true;
                        redirectUrl += AMPERSAND + DUPLICATE_LOGIN_ERROR + true;
                    }
                    if (duplicate) {
                        return CommandResult.createRedirectResult(redirectUrl);
                    }

                    User updatedUser = User.builder().of(user)
                            .setEmail(email)
                            .setLogin(login)
                            .build();
                    if (passwordIsEmpty) {
                        userService.update(updatedUser);
                    } else {
                        userService.updateWithPassword(updatedUser, password);
                    }

                    session.setAttribute(USER, updatedUser);
                    redirectUrl += AMPERSAND + PROFILE_UPDATED + EQUALS_SIGN + true;
                } else {
                    redirectUrl += AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                }
                return CommandResult.createRedirectResult(redirectUrl);
            } else {
                logger.error("Requested profile not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update profile command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
