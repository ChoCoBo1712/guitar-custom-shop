package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.PagePath;
import com.chocobo.customshop.controller.command.validator.UserValidator;
import com.chocobo.customshop.controller.command.validator.ValidationResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.service.MailService;
import com.chocobo.customshop.service.TokenService;
import com.chocobo.customshop.service.impl.MailServiceImpl;
import com.chocobo.customshop.service.impl.TokenServiceImpl;
import com.chocobo.customshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.chocobo.customshop.controller.command.RequestParameter.*;

public class RegisterCommand implements Command {

    private static final String PROTOCOL_DELIMITER = "://";
    private static final String URL_BLANK = "/controller?command=confirm_email&token=";

    private static final String SUBJECT_PROPERTY = "confirmation_mail.subject";
    private static final String BODY_PROPERTY = "confirmation_mail.body";

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        ValidationResult validationResult = UserValidator.validateRegistration(email, login);
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
                return PagePath.ERROR_500_JSP;
            }
            return PagePath.REGISTER_SUCCESS_JSP;
        } else {
            request.setAttribute(VALIDATION_ERROR, validationResult.getErrorMessage());
            return PagePath.REGISTER_JSP;
        }
    }
}
