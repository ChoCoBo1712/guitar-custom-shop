package com.chocobo.customshop.controller.command.impl;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class GoToRegisterPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.REGISTER_JSP;
    }
}