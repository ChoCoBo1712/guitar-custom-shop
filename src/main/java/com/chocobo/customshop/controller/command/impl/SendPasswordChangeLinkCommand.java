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

    private static final String URL_BLANK = "/controller?command=go_to_password_change_page&token=";

    private static final String SUBJECT_PROPERTY = "passwordChangeMail.subject";
    private static final String BODY_PROPERTY = "passwordChangeMail.body";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter(EMAIL);

        CommandResult result;
        try {
            if (!UserServiceImpl.getInstance().isEmailUnique(email)) {
                MailUtil mailUtil = MailUtilImpl.getInstance();
                TokenUtil tokenUtil = TokenUtilImpl.getInstance();

                String mailSubject = mailUtil.getMailProperty(SUBJECT_PROPERTY);
                String bodyTemplate = mailUtil.getMailProperty(BODY_PROPERTY);
                Map<String, Object> claimsMap = new HashMap<>();
                claimsMap.put(EMAIL_CLAIM, email);
                String confirmationUrl = URL_BLANK + tokenUtil.generateToken(claimsMap);
                String confirmationLink = request.getScheme() + PROTOCOL_DELIMITER + request.getServerName() + confirmationUrl;

                String mailBody = String.format(bodyTemplate, confirmationLink);
                mailUtil.sendMail(email, mailSubject, mailBody);

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
