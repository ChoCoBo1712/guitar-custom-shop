package com.chocobo.customshop.web.command.impl;

import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.util.MailUtil;
import com.chocobo.customshop.util.impl.MailUtilImpl;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.GUITAR_ORDERS_URL;
import static com.chocobo.customshop.web.command.RequestAttribute.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class TakeOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final GuitarService guitarService = GuitarServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final MailUtil mailUtil = MailUtilImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Guitar> optionalGuitar = guitarService.findById(entityId);
            if (optionalGuitar.isPresent()) {
                Guitar guitar = optionalGuitar.get();
                long userId = guitar.getUserId();
                Optional<User> optionalUser = userService.findById(userId);
                if (optionalUser.isPresent()) {
                    Guitar updatedGuitar = Guitar.builder().of(guitar)
                            .setOrderStatus(Guitar.OrderStatus.IN_PROGRESS)
                            .build();
                    guitarService.update(updatedGuitar);

                    User user = optionalUser.get();
                    mailUtil.senOrderInProgressMail(user.getEmail(), guitar.getName(),
                            request.getScheme(), request.getServerName());
                } else {
                    logger.error("Requested not found, id = " + userId);
                    return CommandResult.createErrorResult(SC_NOT_FOUND);
                }
                return CommandResult.createRedirectResult(GUITAR_ORDERS_URL);
            } else {
                logger.error("Requested guitar not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during take order command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
