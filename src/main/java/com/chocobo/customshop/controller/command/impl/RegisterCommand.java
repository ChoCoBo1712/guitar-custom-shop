package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.SessionAttribute;
import com.chocobo.customshop.controller.command.validator.UserValidator;
import com.chocobo.customshop.controller.command.validator.ValidationResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.service.MailService;
import com.chocobo.customshop.service.TokenService;
import com.chocobo.customshop.service.impl.MailServiceImpl;
import com.chocobo.customshop.service.impl.TokenServiceImpl;
import com.chocobo.customshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.REGISTER_SUCCESS_URL;
import static com.chocobo.customshop.controller.command.PagePath.REGISTER_URL;
import static com.chocobo.customshop.controller.command.RequestParameter.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.*;
import static com.chocobo.customshop.controller.command.validator.UserValidator.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class RegisterCommand implements Command {

    private static final String PROTOCOL_DELIMITER = "://";
    private static final String URL_BLANK = "/controller?command=confirm_email&token=";

    private static final String SUBJECT_PROPERTY = "confirmation_mail.subject";
    private static final String BODY_PROPERTY = "confirmation_mail.body";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(INVALID_EMAIL_ERROR, false);
        session.setAttribute(INVALID_LOGIN_ERROR, false);

        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        ValidationResult validationResult = validateRegistration(email, login);
        CommandResult result;
        if (validationResult.isValid()) {
            try {
                long userId = UserServiceImpl.getInstance().register(email, login, password);
                MailService mailService = MailServiceImpl.getInstance();
                TokenService tokenService = TokenServiceImpl.getInstance();

                String mailSubject = mailService.getMailProperty(SUBJECT_PROPERTY);
                String bodyTemplate = mailService.getMailProperty(BODY_PROPERTY);
                String confirmationUrl = URL_BLANK + tokenService.generateToken(userId, email);
                String confirmationLink = request.getScheme() + PROTOCOL_DELIMITER + request.getServerName() + confirmationUrl;

                String mailBody = String.format(bodyTemplate, confirmationLink);
                mailService.sendMail(email, mailSubject, mailBody);
            } catch (ServiceException e) {
                return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
            }
            result = new CommandResult(REGISTER_SUCCESS_URL, REDIRECT);
        } else {
            List<String> errorAttributesList = validationResult.getErrorList();
            if (errorAttributesList.contains(SERVICE_EXCEPTION)) {
                return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
            } else {
                errorAttributesList.remove(SERVICE_EXCEPTION);
                errorAttributesList.forEach(errorAttribute -> session.setAttribute(errorAttribute, true));
            }
            result = new CommandResult(REGISTER_URL, REDIRECT);
        }
        return result;
    }
}
