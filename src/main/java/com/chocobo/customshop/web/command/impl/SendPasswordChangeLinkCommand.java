package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.util.MailUtil;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.impl.MailUtilImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.EMAIL;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class SendPasswordChangeLinkCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final MailUtil mailUtil = MailUtilImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter(EMAIL);

        try {
            if (!userService.isEmailUnique(email)) {
                mailUtil.sendPasswordChangeMail(email, request.getScheme(), request.getServerName());

                session.setAttribute(PASSWORD_CHANGE, true);
                return CommandResult.createRedirectResult(TOKEN_SENT_URL);
            } else {
                session.setAttribute(FORGOT_PASSWORD_ERROR, true);
                return CommandResult.createRedirectResult(FORGOT_PASSWORD_URL);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during send password change link command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
