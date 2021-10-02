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

import static com.chocobo.customshop.web.command.PagePath.PROFILE_URL;
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
                return validateAndUpdateUser(session, email, login, password, optionalUser);
            } else {
                logger.error("Requested profile not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update profile command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 02.10.2021 This method is mostly dedicated to validation - move it to some validator or something
    private CommandResult validateAndUpdateUser(HttpSession session, String email, String login, String password, Optional<User> optionalUser) throws ServiceException {
        User user = optionalUser.get();
        String previousEmail = user.getEmail();
        String previousLogin = user.getLogin();

        boolean emailsMatch = StringUtils.equals(email, previousEmail);
        boolean loginsMatch = StringUtils.equals(login, previousLogin);
        boolean passwordIsEmpty = StringUtils.isEmpty(password);

        boolean valid = emailsMatch || EmailValidator.getInstance().validate(email)
                && loginsMatch || LoginValidator.getInstance().validate(login)
                && passwordIsEmpty || PasswordValidator.getInstance().validate(password);

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
                return CommandResult.createRedirectResult(PROFILE_URL);
            }

            User updatedUser = updateUser(email, login, password, user, passwordIsEmpty);

            // TODO: 02.10.2021 Sessions are not suitable to pass params
            session.setAttribute(USER, updatedUser);
            session.setAttribute(PROFILE_UPDATED, true);
        } else {
            session.setAttribute(VALIDATION_ERROR, true);
        }
        return CommandResult.createRedirectResult(PROFILE_URL);
    }

    private User updateUser(String email, String login, String password, User existingUser, boolean passwordIsEmpty) throws ServiceException {
        User updatedUser = User.builder().of(existingUser)
                .setEmail(email)
                .setLogin(login)
                .build();
        if (passwordIsEmpty) {
            userService.update(updatedUser);
        } else {
            userService.updateWithPassword(updatedUser, password);
        }
        return existingUser;
    }
}
