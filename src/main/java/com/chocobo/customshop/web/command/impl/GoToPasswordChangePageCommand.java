package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.util.TokenUtil;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.impl.TokenUtilImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.*;
import static com.chocobo.customshop.web.command.PagePath.PASSWORD_CHANGE_JSP;
import static com.chocobo.customshop.web.command.RequestAttribute.EMAIL;
import static com.chocobo.customshop.web.command.RequestAttribute.TOKEN;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class GoToPasswordChangePageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final TokenUtil tokenUtil = TokenUtilImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        try {
            String token = request.getParameter(TOKEN);
            Map<String, Object> tokenContent = tokenUtil.parseToken(token);
            String email = (String) tokenContent.get(EMAIL_CLAIM);

            if (!userService.isEmailUnique(email)) {
                request.setAttribute(EMAIL, email);
                return CommandResult.createForwardResult(PASSWORD_CHANGE_JSP);
            } else {
                logger.error("Got invalid token");
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during go to password change page command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
