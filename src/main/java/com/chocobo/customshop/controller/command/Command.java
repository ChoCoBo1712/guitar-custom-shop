package com.chocobo.customshop.controller.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    CommandResult execute(HttpServletRequest request);
}
