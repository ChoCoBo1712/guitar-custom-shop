package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.TokenUtil;
import com.chocobo.customshop.util.impl.TokenUtilImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.ACTIVATION_SUCCESS_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.TOKEN;
import static com.chocobo.customshop.model.entity.User.UserStatus.CONFIRMED;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.ID_CLAIM;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class ConfirmEmailCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        TokenUtil tokenUtil = TokenUtilImpl.getInstance();

        try {
            String token = request.getParameter(TOKEN);
            Map<String, Object> tokenContent = tokenUtil.parseToken(token);
            long userId = ((Double) tokenContent.get(ID_CLAIM)).longValue();
            String email = (String) tokenContent.get(EMAIL_CLAIM);

            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (StringUtils.equals(email, user.getEmail()) && user.getStatus() != CONFIRMED) {
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
