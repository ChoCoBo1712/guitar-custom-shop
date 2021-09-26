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

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.controller.command.PagePath.PASSWORD_CHANGE_JSP;
import static com.chocobo.customshop.controller.command.PagePath.TOKEN_SUCCESS_URL;
import static com.chocobo.customshop.controller.command.RequestAttribute.EMAIL;
import static com.chocobo.customshop.controller.command.RequestAttribute.TOKEN;
import static com.chocobo.customshop.controller.command.SessionAttribute.EMAIL_CONFIRMATION;
import static com.chocobo.customshop.model.entity.User.UserStatus.CONFIRMED;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.ID_CLAIM;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GoToPasswordChangePageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        CommandResult result;
        try {
            String token = request.getParameter(TOKEN);
            Map<String, Object> tokenContent = TokenUtilImpl.getInstance().parseToken(token);
            String email = (String) tokenContent.get(EMAIL_CLAIM);

            if (!UserServiceImpl.getInstance().isEmailUnique(email)) {
                request.setAttribute(EMAIL, email);
                request.setAttribute(TOKEN, token);
                result = new CommandResult(PASSWORD_CHANGE_JSP, FORWARD);
            } else {
                logger.error("Got invalid token");
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during go to password change page command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
