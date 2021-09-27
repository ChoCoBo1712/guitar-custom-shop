package com.chocobo.customshop.controller.command;

import com.chocobo.customshop.controller.command.impl.*;
import com.chocobo.customshop.controller.command.impl.admin.ajax.*;
import com.chocobo.customshop.controller.command.impl.admin.body.*;
import com.chocobo.customshop.controller.command.impl.admin.guitar.*;
import com.chocobo.customshop.controller.command.impl.admin.neck.*;
import com.chocobo.customshop.controller.command.impl.admin.pickup.*;
import com.chocobo.customshop.controller.command.impl.admin.user.*;
import com.chocobo.customshop.controller.command.impl.admin.wood.*;
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
        commandMap.put(CommandType.GO_TO_TOKEN_SENT_PAGE, new GoToTokenSentPageCommand());
        commandMap.put(CommandType.GO_TO_TOKEN_SUCCESS_PAGE, new GoToTokenSuccessPageCommand());
        commandMap.put(CommandType.GO_TO_USERS_PAGE, new GoToUsersPageCommand());
        commandMap.put(CommandType.GO_TO_CREATE_USER_PAGE, new GoToCreateUserPageCommand());
        commandMap.put(CommandType.GO_TO_EDIT_USER_PAGE, new GoToEditUserPageCommand());
        commandMap.put(CommandType.GO_TO_WOODS_PAGE, new GoToWoodsPageCommand());
        commandMap.put(CommandType.GO_TO_CREATE_WOOD_PAGE, new GoToCreateWoodPageCommand());
        commandMap.put(CommandType.GO_TO_EDIT_WOOD_PAGE, new GoToEditWoodPageCommand());
        commandMap.put(CommandType.GO_TO_BODIES_PAGE, new GoToBodiesPageCommand());
        commandMap.put(CommandType.GO_TO_CREATE_BODY_PAGE, new GoToCreateBodyPageCommand());
        commandMap.put(CommandType.GO_TO_EDIT_BODY_PAGE, new GoToEditBodyPageCommand());
        commandMap.put(CommandType.GO_TO_PICKUPS_PAGE, new GoToPickupsPageCommand());
        commandMap.put(CommandType.GO_TO_CREATE_PICKUP_PAGE, new GoToCreatePickupPageCommand());
        commandMap.put(CommandType.GO_TO_EDIT_PICKUP_PAGE, new GoToEditPickupPageCommand());
        commandMap.put(CommandType.GO_TO_NECKS_PAGE, new GoToNecksPageCommand());
        commandMap.put(CommandType.GO_TO_CREATE_NECK_PAGE, new GoToCreateNeckPageCommand());
        commandMap.put(CommandType.GO_TO_EDIT_NECK_PAGE, new GoToEditNeckPageCommand());
        commandMap.put(CommandType.GO_TO_GUITARS_PAGE, new GoToGuitarsPageCommand());
        commandMap.put(CommandType.GO_TO_CREATE_GUITAR_PAGE, new GoToCreateGuitarPageCommand());
        commandMap.put(CommandType.GO_TO_EDIT_GUITAR_PAGE, new GoToEditGuitarPageCommand());
        commandMap.put(CommandType.GO_TO_PROFILE_PAGE, new GoToProfilePageCommand());
        commandMap.put(CommandType.GO_TO_FORGOT_PASSWORD_PAGE, new GoToForgotPasswordPageCommand());

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
        commandMap.put(CommandType.CREATE_WOOD, new CreateWoodCommand());
        commandMap.put(CommandType.UPDATE_WOOD, new UpdateWoodCommand());
        commandMap.put(CommandType.DELETE_WOOD, new DeleteWoodCommand());
        commandMap.put(CommandType.GET_BODIES, new GetBodiesCommand());
        commandMap.put(CommandType.CREATE_BODY, new CreateBodyCommand());
        commandMap.put(CommandType.UPDATE_BODY, new UpdateBodyCommand());
        commandMap.put(CommandType.DELETE_BODY, new DeleteBodyCommand());
        commandMap.put(CommandType.GET_PICKUPS, new GetPickupsCommand());
        commandMap.put(CommandType.CREATE_PICKUP, new CreatePickupCommand());
        commandMap.put(CommandType.UPDATE_PICKUP, new UpdatePickupCommand());
        commandMap.put(CommandType.DELETE_PICKUP, new DeletePickupCommand());
        commandMap.put(CommandType.GET_NECKS, new GetNecksCommand());
        commandMap.put(CommandType.CREATE_NECK, new CreateNeckCommand());
        commandMap.put(CommandType.UPDATE_NECK, new UpdateNeckCommand());
        commandMap.put(CommandType.DELETE_NECK, new DeleteNeckCommand());
        commandMap.put(CommandType.GET_GUITARS, new GetGuitarsCommand());
        commandMap.put(CommandType.CREATE_GUITAR, new CreateGuitarCommand());
        commandMap.put(CommandType.UPDATE_GUITAR, new UpdateGuitarCommand());
        commandMap.put(CommandType.DELETE_GUITAR, new DeleteGuitarCommand());
        commandMap.put(CommandType.SEND_CONFIRMATION_LINK, new SendConfirmationLinkCommand());
        commandMap.put(CommandType.SEND_PASSWORD_CHANGE_LINK, new SendPasswordChangeLinkCommand());
        commandMap.put(CommandType.GO_TO_PASSWORD_CHANGE_PAGE, new GoToPasswordChangePageCommand());
        commandMap.put(CommandType.PASSWORD_CHANGE, new PasswordChangeCommand());
        commandMap.put(CommandType.UPDATE_PROFILE, new UpdateProfileCommand());
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
