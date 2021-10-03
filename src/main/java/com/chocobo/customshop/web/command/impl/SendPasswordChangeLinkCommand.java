package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.MailUtil;
import com.chocobo.customshop.util.impl.MailUtilImpl;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class SendPasswordChangeLinkCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final MailUtil mailUtil = MailUtilImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);

        try {
            if (!userService.isEmailUnique(email)) {
                mailUtil.sendPasswordChangeMail(email, request.getScheme(), request.getServerName());

                String redirectUrl = TOKEN_SENT_URL
                        + AMPERSAND + PASSWORD_CHANGE + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            } else {
                String redirectUrl = FORGOT_PASSWORD_URL
                        + AMPERSAND + FORGOT_PASSWORD_ERROR + EQUALS_SIGN + true;
                return CommandResult.createRedirectResult(redirectUrl);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during send password change link command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
