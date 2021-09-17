package com.chocobo.customshop.controller.command;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {

    CommandResult execute(HttpServletRequest request);
}
