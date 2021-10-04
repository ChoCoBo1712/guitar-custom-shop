package com.chocobo.customshop.web;

import com.chocobo.customshop.web.command.*;
import jakarta.resource.spi.AuthenticationMechanism;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.chocobo.customshop.web.command.RequestAttribute.COMMAND;
import static com.chocobo.customshop.web.command.SessionAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.*;

/**
 * {@code ControllerServlet} class is a subclass of {@link HttpServlet} class.
 * It processes all requests to {@code /controller} url after filtering.
 * @author Evgeniy Sokolchik
 */
@WebServlet(urlPatterns = "/controller")
@MultipartConfig(
        fileSizeThreshold = 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class ControllerServlet extends HttpServlet {


    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandParameter = request.getParameter(COMMAND);
        Optional<Pair<Command, Set<AppRole>>> optionalPair = CommandProvider.getCommand(commandParameter);

        if (optionalPair.isPresent()) {
            Pair<Command, Set<AppRole>> pair = optionalPair.get();
            Set<AppRole> appRoles = pair.getRight();

            AppRole currentUserRole = (AppRole) request.getSession().getAttribute(USER_ROLE);

            if (appRoles.contains(currentUserRole)) {
                CommandResult commandResult = pair.getLeft().execute(request);
                CommandResult.RouteType routeType = commandResult.getRouteType();

                switch (routeType) {
                    case FORWARD -> {
                        String forwardPath = commandResult.getRoute();
                        request.getRequestDispatcher(forwardPath).forward(request, response);
                    }
                    case REDIRECT -> {
                        String redirectUrl = commandResult.getRoute();
                        response.sendRedirect(redirectUrl);
                    }
                    case ERROR -> {
                        int errorCode = commandResult.getErrorCode();
                        response.sendError(errorCode);
                    }
                    case JSON -> {
                        String jsonResponse = commandResult.getRoute();
                        response.getWriter().write(jsonResponse);
                    }
                    default -> {
                        logger.error("Invalid route type: " + routeType.name());
                        response.sendError(SC_INTERNAL_SERVER_ERROR);
                    }
                }
            } else {
                response.sendError(SC_FORBIDDEN);
            }
        } else {
            logger.error("Specified command not found");
            response.sendError(SC_NOT_FOUND);
        }
    }
}
