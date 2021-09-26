package com.chocobo.customshop.controller;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandProvider;
import com.chocobo.customshop.controller.command.CommandResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.RequestAttribute.COMMAND;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

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
        Optional<Command> command = CommandProvider.getCommand(commandParameter);
        if (command.isPresent()) {
            CommandResult commandResult = command.get().execute(request);

            Object route = commandResult.getRoute();
            CommandResult.RouteType routeType = commandResult.getRouteType();

            switch (routeType) {
                case FORWARD -> {
                    String forwardPath = (String) route;
                    request.getRequestDispatcher(forwardPath).forward(request, response);
                }
                case REDIRECT -> {
                    String redirectUrl = (String) route;
                    response.sendRedirect(redirectUrl);
                }
                case ERROR -> {
                    int errorCode = (Integer) route;
                    response.sendError(errorCode);
                }
                case JSON -> {
                    String jsonResponse = (String) route;
                    response.getWriter().write(jsonResponse);
                }
                default -> {
                    logger.error("Invalid route type: " + routeType.name());
                    response.sendError(SC_INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            logger.error("Specified command not found");
            response.sendError(SC_NOT_FOUND);
        }
    }
}