package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.service.TokenService;
import com.chocobo.customshop.service.UserService;
import com.chocobo.customshop.service.impl.TokenServiceImpl;
import com.chocobo.customshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ACTIVATION_SUCCESS_URL;
import static com.chocobo.customshop.controller.command.RequestParameter.TOKEN;
import static com.chocobo.customshop.model.entity.User.UserStatus.CONFIRMED;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class ConfirmEmailCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final String ID_CLAIM = "id";
    private static final String EMAIL_CLAIM = "email";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        TokenService tokenService = TokenServiceImpl.getInstance();

        try {
            String token = request.getParameter(TOKEN);
            Map<String, Object> tokenContent = tokenService.parseToken(token);
            long userId = ((Double) tokenContent.get(ID_CLAIM)).longValue();
            String email = (String) tokenContent.get(EMAIL_CLAIM);

            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (email.equals(user.getEmail()) && user.getStatus() != CONFIRMED) {
                    User updatedUser = User.builder().of(user)
                            .setStatus(CONFIRMED)
                            .build();
                    userService.update(updatedUser);
                }
            }

            return new CommandResult(ACTIVATION_SUCCESS_URL, REDIRECT);
        } catch (ServiceException e) {
            logger.error("An error occurred during confirm email command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
    }
}
