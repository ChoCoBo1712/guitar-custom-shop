package com.chocobo.customshop.web.command;

import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {

    CommandResult execute(HttpServletRequest request);
}
