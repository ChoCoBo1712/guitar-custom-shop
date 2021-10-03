package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.TokenUtil;
import com.chocobo.customshop.util.impl.TokenUtilImpl;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static com.chocobo.customshop.model.entity.User.UserStatus.CONFIRMED;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.ID_CLAIM;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.EMAIL_CONFIRMATION;
import static com.chocobo.customshop.web.command.RequestAttribute.TOKEN;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ConfirmEmailCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();
    private final TokenUtil tokenUtil = TokenUtilImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
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

                    String redirectUrl = TOKEN_SUCCESS_URL
                            + AMPERSAND + EMAIL_CONFIRMATION + EQUALS_SIGN + true;
                    return CommandResult.createRedirectResult(redirectUrl);
                }
            }
            logger.error("Got invalid token");
            return CommandResult.createErrorResult(SC_NOT_FOUND);
        } catch (ServiceException e) {
            logger.error("An error occurred during confirm email command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
