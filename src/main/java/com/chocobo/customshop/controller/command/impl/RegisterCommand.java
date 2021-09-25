package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.impl.EmailValidator;
import com.chocobo.customshop.model.validator.impl.LoginValidator;
import com.chocobo.customshop.model.validator.impl.PasswordValidator;
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
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.SessionAttribute.*;
import static com.chocobo.customshop.model.entity.User.UserRole.CLIENT;
import static com.chocobo.customshop.model.entity.User.UserStatus.NOT_CONFIRMED;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.ID_CLAIM;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final String PROTOCOL_DELIMITER = "://";
    private static final String URL_BLANK = "/controller?command=confirm_email&token=";

    private static final String SUBJECT_PROPERTY = "confirmationMail.subject";
    private static final String BODY_PROPERTY = "confirmationMail.body";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        CommandResult result;
        try {
            boolean valid = EmailValidator.getInstance().validate(email);
            valid &= LoginValidator.getInstance().validate(login);
            valid &= PasswordValidator.getInstance().validate(password);

            if (valid) {
                UserService userService = UserServiceImpl.getInstance();
                boolean duplicate = false;

                if (!userService.isEmailUnique(email)) {
                    duplicate = true;
                    session.setAttribute(DUPLICATE_EMAIL_ERROR, true);
                }
                if (!userService.isLoginUnique(login)) {
                    duplicate = true;
                    session.setAttribute(DUPLICATE_LOGIN_ERROR, true);
                }
                if (duplicate) {
                    return new CommandResult(ADMIN_CREATE_USER_URL, REDIRECT);
                }

                long userId = UserServiceImpl.getInstance().register(email, login, password, CLIENT, NOT_CONFIRMED);

                MailUtil mailUtil = MailUtilImpl.getInstance();
                TokenUtil tokenUtil = TokenUtilImpl.getInstance();

                String mailSubject = mailUtil.getMailProperty(SUBJECT_PROPERTY);
                String bodyTemplate = mailUtil.getMailProperty(BODY_PROPERTY);
                Map<String, Object> claimsMap = new HashMap<>();
                claimsMap.put(ID_CLAIM, userId);
                claimsMap.put(EMAIL_CLAIM, email);
                String confirmationUrl = URL_BLANK + tokenUtil.generateToken(claimsMap);
                String confirmationLink = request.getScheme() + PROTOCOL_DELIMITER + request.getServerName() + confirmationUrl;

                String mailBody = String.format(bodyTemplate, confirmationLink);
                mailUtil.sendMail(email, mailSubject, mailBody);
                result = new CommandResult(REGISTER_SUCCESS_URL, REDIRECT);
            } else {
                session.setAttribute(VALIDATION_ERROR, true);
                result = new CommandResult(REGISTER_URL, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during register command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
