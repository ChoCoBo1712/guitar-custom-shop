package com.chocobo.customshop.controller;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandProvider;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.controller.command.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.RequestParameter.COMMAND;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {

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

            String route = commandResult.getRoute();
            CommandResult.RouteType routeType = commandResult.getRouteType();

            switch (routeType) {
                case FORWARD -> request.getRequestDispatcher(route).forward(request, response);
                case REDIRECT -> response.sendRedirect(route);
                default -> {
                    logger.error("Invalid route type: " + routeType.name());
                    response.sendRedirect(ERROR_500_JSP);
                }
            }
        } else {
            request.getRequestDispatcher(ERROR_404_JSP).forward(request, response);
        }
    }
}
