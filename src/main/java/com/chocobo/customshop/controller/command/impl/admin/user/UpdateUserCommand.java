package com.chocobo.customshop.controller.command.impl.admin.user;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.ValidationService;
import com.chocobo.customshop.util.impl.ValidationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestParameter.*;
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
                ValidationService validationService = ValidationServiceImpl.getInstance();
                Pair<Boolean, List<String>> validationResult = validationService
                        .validateUserUpdate(email, login, previousEmail, previousLogin);
                if (validationResult.getLeft()) {
                    User updatedUser = User.builder().of(user)
                            .setEmail(email)
                            .setLogin(login)
                            .setRole(role)
                            .setStatus(status)
                            .build();
                    userService.update(updatedUser);
                    result = new CommandResult(ADMIN_USERS_URL, REDIRECT);
                } else {
                    List<String> errorAttributesList = validationResult.getRight();
                    errorAttributesList.forEach(errorAttribute -> session.setAttribute(errorAttribute, true));
                    String currentEditUserPageUrl = ADMIN_EDIT_USER_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    result = new CommandResult(currentEditUserPageUrl, REDIRECT);
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
