package com.chocobo.customshop.controller;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandProvider;
import com.chocobo.customshop.controller.command.PagePath;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.RequestParameter.*;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {

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
            String pageName = command.get().execute(request);
            request.getRequestDispatcher(pageName).forward(request, response);
        } else {
            request.getRequestDispatcher(PagePath.ERROR_404_JSP).forward(request, response);
        }
    }
}
