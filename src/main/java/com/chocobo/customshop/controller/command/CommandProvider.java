package com.chocobo.customshop.controller.command;

import com.chocobo.customshop.controller.command.impl.*;
import com.chocobo.customshop.controller.command.impl.admin.ajax.*;
import com.chocobo.customshop.controller.command.impl.admin.body.*;
import com.chocobo.customshop.controller.command.impl.admin.guitar.*;
import com.chocobo.customshop.controller.command.impl.admin.neck.*;
import com.chocobo.customshop.controller.command.impl.admin.pickup.*;
import com.chocobo.customshop.controller.command.impl.admin.user.*;
import com.chocobo.customshop.controller.command.impl.admin.wood.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.chocobo.customshop.controller.command.AppRole.*;

public class CommandProvider {

    public static final Logger logger = LogManager.getLogger();
    private static final Map<CommandType, Pair<Command, Set<AppRole>>> commandMap = new EnumMap<>(CommandType.class);

    static {
        // GoTo commands
        commandMap.put(CommandType.GO_TO_INDEX_PAGE, Pair.of(new GoToIndexPageCommand(),
                new HashSet<>(Arrays.asList(GUEST, NOT_CONFIRMED, CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.GO_TO_LOGIN_PAGE, Pair.of(new GoToLoginPageCommand(),
                new HashSet<>(Collections.singletonList(GUEST))));
        commandMap.put(CommandType.GO_TO_REGISTER_PAGE, Pair.of(new GoToRegisterPageCommand(),
                new HashSet<>(Collections.singletonList(GUEST))));
        commandMap.put(CommandType.GO_TO_TOKEN_SENT_PAGE, Pair.of(new GoToTokenSentPageCommand(),
                new HashSet<>(Arrays.asList(GUEST, NOT_CONFIRMED, CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.GO_TO_TOKEN_SUCCESS_PAGE, Pair.of(new GoToTokenSuccessPageCommand(),
                new HashSet<>(Arrays.asList(GUEST, NOT_CONFIRMED, CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.GO_TO_USERS_PAGE, Pair.of(new GoToUsersPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_CREATE_USER_PAGE, Pair.of(new GoToCreateUserPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_EDIT_USER_PAGE, Pair.of(new GoToEditUserPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_WOODS_PAGE, Pair.of(new GoToWoodsPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_CREATE_WOOD_PAGE, Pair.of(new GoToCreateWoodPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_EDIT_WOOD_PAGE, Pair.of(new GoToEditWoodPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_BODIES_PAGE, Pair.of(new GoToBodiesPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_CREATE_BODY_PAGE, Pair.of(new GoToCreateBodyPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_EDIT_BODY_PAGE, Pair.of(new GoToEditBodyPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_PICKUPS_PAGE, Pair.of(new GoToPickupsPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_CREATE_PICKUP_PAGE, Pair.of(new GoToCreatePickupPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_EDIT_PICKUP_PAGE, Pair.of(new GoToEditPickupPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_NECKS_PAGE, Pair.of(new GoToNecksPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_CREATE_NECK_PAGE, Pair.of(new GoToCreateNeckPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_EDIT_NECK_PAGE, Pair.of(new GoToEditNeckPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_GUITARS_PAGE, Pair.of(new GoToGuitarsPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_CREATE_GUITAR_PAGE, Pair.of(new GoToCreateGuitarPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_EDIT_GUITAR_PAGE, Pair.of(new GoToEditGuitarPageCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GO_TO_PROFILE_PAGE, Pair.of(new GoToProfilePageCommand(),
                new HashSet<>(Arrays.asList(NOT_CONFIRMED, CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.GO_TO_FORGOT_PASSWORD_PAGE, Pair.of(new GoToForgotPasswordPageCommand(),
                new HashSet<>(Collections.singletonList(GUEST))));
        commandMap.put(CommandType.GO_TO_CONSTRUCT_GUITAR_PAGE, Pair.of(new GoToConstructGuitarPage(),
                new HashSet<>(Arrays.asList(CLIENT, ADMIN))));
        commandMap.put(CommandType.GO_TO_PASSWORD_CHANGE_PAGE, Pair.of(new GoToPasswordChangePageCommand(),
                new HashSet<>(Collections.singletonList(GUEST))));
        commandMap.put(CommandType.GO_TO_MY_GUITARS_PAGE, Pair.of(new GoToMyGuitarsPageCommand(),
                new HashSet<>(Arrays.asList(CLIENT, ADMIN))));
        commandMap.put(CommandType.GO_TO_GUITAR_ORDERS_PAGE, Pair.of(new GoToGuitarOrdersPageCommand(),
                new HashSet<>(Arrays.asList(MAKER, ADMIN))));
        commandMap.put(CommandType.GO_TO_FINISH_ORDER_PAGE, Pair.of(new GoToFinishOrderPageCommand(),
                new HashSet<>(Arrays.asList(MAKER, ADMIN))));

        // other commands
        commandMap.put(CommandType.REGISTER, Pair.of(new RegisterCommand(),
                new HashSet<>(Collections.singletonList(GUEST))));
        commandMap.put(CommandType.LOGIN, Pair.of(new LoginCommand(),
                new HashSet<>(Collections.singletonList(GUEST))));
        commandMap.put(CommandType.LOGOUT, Pair.of(new LogoutCommand(),
                new HashSet<>(Arrays.asList(NOT_CONFIRMED, CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CONFIRM_EMAIL, Pair.of(new ConfirmEmailCommand(),
                new HashSet<>(Arrays.asList(GUEST, NOT_CONFIRMED, CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.GET_USERS, Pair.of(new GetUsersCommand(),
                new HashSet<>(Arrays.asList(CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CREATE_USER, Pair.of(new CreateUserCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.UPDATE_USER, Pair.of(new UpdateUserCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.DELETE_USER, Pair.of(new DeleteUserCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GET_WOODS, Pair.of(new GetWoodsCommand(),
                new HashSet<>(Arrays.asList(CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CREATE_WOOD, Pair.of(new CreateWoodCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.UPDATE_WOOD, Pair.of(new UpdateWoodCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.DELETE_WOOD, Pair.of(new DeleteWoodCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GET_BODIES, Pair.of(new GetBodiesCommand(),
                new HashSet<>(Arrays.asList(CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CREATE_BODY, Pair.of(new CreateBodyCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.UPDATE_BODY, Pair.of(new UpdateBodyCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.DELETE_BODY, Pair.of(new DeleteBodyCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GET_PICKUPS, Pair.of(new GetPickupsCommand(),
                new HashSet<>(Arrays.asList(CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CREATE_PICKUP, Pair.of(new CreatePickupCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.UPDATE_PICKUP, Pair.of(new UpdatePickupCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.DELETE_PICKUP, Pair.of(new DeletePickupCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GET_NECKS, Pair.of(new GetNecksCommand(),
                new HashSet<>(Arrays.asList(CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CREATE_NECK, Pair.of(new CreateNeckCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.UPDATE_NECK, Pair.of(new UpdateNeckCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.DELETE_NECK, Pair.of(new DeleteNeckCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.GET_GUITARS, Pair.of(new GetGuitarsCommand(),
                new HashSet<>(Arrays.asList(CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CREATE_GUITAR, Pair.of(new CreateGuitarCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.UPDATE_GUITAR, Pair.of(new UpdateGuitarCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.DELETE_GUITAR, Pair.of(new DeleteGuitarCommand(),
                new HashSet<>(Collections.singletonList(ADMIN))));
        commandMap.put(CommandType.SEND_CONFIRMATION_LINK, Pair.of(new SendConfirmationLinkCommand(),
                new HashSet<>(Arrays.asList(GUEST, NOT_CONFIRMED))));
        commandMap.put(CommandType.SEND_PASSWORD_CHANGE_LINK, Pair.of(new SendPasswordChangeLinkCommand(),
                new HashSet<>(Arrays.asList(GUEST, NOT_CONFIRMED))));
        commandMap.put(CommandType.PASSWORD_CHANGE, Pair.of(new PasswordChangeCommand(),
                new HashSet<>(Arrays.asList(GUEST, NOT_CONFIRMED))));
        commandMap.put(CommandType.UPDATE_PROFILE, Pair.of(new UpdateProfileCommand(),
                new HashSet<>(Arrays.asList(NOT_CONFIRMED, CLIENT, MAKER, ADMIN))));
        commandMap.put(CommandType.CONSTRUCT_GUITAR, Pair.of(new ConstructGuitarCommand(),
                new HashSet<>(Arrays.asList(CLIENT, ADMIN))));
        commandMap.put(CommandType.TAKE_ORDER, Pair.of(new TakeOrderCommand(),
                new HashSet<>(Arrays.asList(MAKER, ADMIN))));
        commandMap.put(CommandType.FINISH_ORDER, Pair.of(new FinishOrderCommand(),
                new HashSet<>(Arrays.asList(MAKER, ADMIN))));
    }

    public static Optional<Pair<Command, Set<AppRole>>> getCommand(String action) {
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
