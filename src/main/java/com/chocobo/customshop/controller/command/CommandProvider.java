package com.chocobo.customshop.controller.command;

import com.chocobo.customshop.controller.command.impl.*;
import com.chocobo.customshop.controller.command.impl.admin.ajax.GetWoodsCommand;
import com.chocobo.customshop.controller.command.impl.admin.user.*;
import com.chocobo.customshop.controller.command.impl.admin.ajax.GetUsersCommand;
import com.chocobo.customshop.controller.command.impl.admin.wood.GoToWoodsPageCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class CommandProvider {

    public static final Logger logger = LogManager.getLogger();
    private static final Map<CommandType, Command> commandMap = new EnumMap<>(CommandType.class);

    static {
        // GoTo commands
        commandMap.put(CommandType.GO_TO_INDEX_PAGE, new GoToIndexPageCommand());
        commandMap.put(CommandType.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commandMap.put(CommandType.GO_TO_REGISTER_PAGE, new GoToRegisterPageCommand());
        commandMap.put(CommandType.GO_TO_REGISTER_SUCCESS_PAGE, new GoToRegisterSuccessPageCommand());
        commandMap.put(CommandType.GO_TO_ACTIVATION_SUCCESS_PAGE, new GoToActivationSuccessPageCommand());
        commandMap.put(CommandType.GO_TO_USERS_PAGE, new GoToUsersPageCommand());
        commandMap.put(CommandType.GO_TO_CREATE_USER_PAGE, new GoToCreateUserPageCommand());
        commandMap.put(CommandType.GO_TO_EDIT_USER_PAGE, new GoToEditUserPageCommand());
        commandMap.put(CommandType.GO_TO_WOODS_PAGE, new GoToWoodsPageCommand());

        // other commands
        commandMap.put(CommandType.REGISTER, new RegisterCommand());
        commandMap.put(CommandType.LOGIN, new LoginCommand());
        commandMap.put(CommandType.LOGOUT, new LogoutCommand());
        commandMap.put(CommandType.CONFIRM_EMAIL, new ConfirmEmailCommand());
        commandMap.put(CommandType.GET_USERS, new GetUsersCommand());
        commandMap.put(CommandType.CREATE_USER, new CreateUserCommand());
        commandMap.put(CommandType.UPDATE_USER, new UpdateUserCommand());
        commandMap.put(CommandType.DELETE_USER, new DeleteUserCommand());
        commandMap.put(CommandType.GET_WOODS, new GetWoodsCommand());
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
