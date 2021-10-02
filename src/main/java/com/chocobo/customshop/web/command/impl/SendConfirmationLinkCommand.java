package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.util.MailUtil;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.util.impl.MailUtilImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.TOKEN_SENT_URL;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class SendConfirmationLinkCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final MailUtil mailUtil = MailUtilImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = String.valueOf(session.getAttribute(USER_EMAIL));
        long userId = Long.parseLong(session.getAttribute(USER_ID).toString());

        try {
            mailUtil.sendConfirmationMail(userId, email, request.getScheme(), request.getServerName());

            session.setAttribute(EMAIL_CONFIRMATION, true);
            return CommandResult.createRedirectResult(TOKEN_SENT_URL);
        } catch (ServiceException e) {
            logger.error("An error occurred during send confirmation link command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
