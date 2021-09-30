package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.MailUtil;
import com.chocobo.customshop.util.TokenUtil;
import com.chocobo.customshop.util.impl.MailUtilImpl;
import com.chocobo.customshop.util.impl.TokenUtilImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.EMAIL;
import static com.chocobo.customshop.controller.command.SessionAttribute.*;
import static com.chocobo.customshop.util.impl.MailUtilImpl.PROTOCOL_DELIMITER;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class SendPasswordChangeLinkCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter(EMAIL);

        CommandResult result;
        try {
            if (!UserServiceImpl.getInstance().isEmailUnique(email)) {
                MailUtilImpl.getInstance().sendPasswordChangeMail(email, request.getScheme(), request.getServerName());

                session.setAttribute(PASSWORD_CHANGE, true);
                result = new CommandResult(TOKEN_SENT_URL, REDIRECT);
            } else {
                session.setAttribute(FORGOT_PASSWORD_ERROR, true);
                result = new CommandResult(FORGOT_PASSWORD_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during send password change link command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
