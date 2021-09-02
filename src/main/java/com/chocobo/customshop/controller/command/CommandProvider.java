package com.chocobo.customshop.controller.command;

import com.chocobo.customshop.controller.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class CommandProvider {

    public static final Logger logger = LogManager.getLogger();
    private static final Map<CommandType, Command> commandMap = new EnumMap<>(CommandType.class);

    static {
        commandMap.put(CommandType.GO_TO_INDEX_PAGE, new GoToIndexPageCommand());
        commandMap.put(CommandType.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commandMap.put(CommandType.GO_TO_REGISTER_PAGE, new GoToRegisterPageCommand());
        commandMap.put(CommandType.REGISTER, new RegisterCommand());
        commandMap.put(CommandType.LOGIN, new LoginCommand());
        commandMap.put(CommandType.CONFIRM_EMAIL, new ConfirmEmailCommand());
    }

    public static Optional<Command> getCommand(String action) {
        CommandType type;
        try {
            type = CommandType.valueOf(action.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Unknown command type", e);
            return Optional.empty();
        }
        return Optional.ofNullable(commandMap.get(type));
    }
}
