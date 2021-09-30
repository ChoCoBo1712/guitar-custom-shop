package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
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
import static com.chocobo.customshop.controller.command.PagePath.TOKEN_SENT_URL;
import static com.chocobo.customshop.controller.command.SessionAttribute.EMAIL_CONFIRMATION;
import static com.chocobo.customshop.controller.command.SessionAttribute.USER;
import static com.chocobo.customshop.util.impl.MailUtilImpl.PROTOCOL_DELIMITER;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.ID_CLAIM;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class SendConfirmationLinkCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final String URL_BLANK = "/controller?command=confirm_email&token=";

    private static final String SUBJECT_PROPERTY = "confirmationMail.subject";
    private static final String BODY_PROPERTY = "confirmationMail.body";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String email = user.getEmail();
        long userId = user.getEntityId();

        CommandResult result;
        try {
            MailUtilImpl.getInstance().sendConfirmationMail(userId, email, request.getScheme(), request.getServerName());

            session.setAttribute(EMAIL_CONFIRMATION, true);
            result = new CommandResult(TOKEN_SENT_URL, REDIRECT);
        } catch (ServiceException e) {
            logger.error("An error occurred during send confirmation link command execution", e);
            result = new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
