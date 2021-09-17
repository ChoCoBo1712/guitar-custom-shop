package com.chocobo.customshop.controller.command.impl.admin.wood;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import com.chocobo.customshop.util.ValidationUtil;
import com.chocobo.customshop.util.impl.ValidationUtilImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.controller.command.PagePath.*;
import static com.chocobo.customshop.controller.command.PagePath.EQUALS_SIGN;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static com.chocobo.customshop.controller.command.RequestAttribute.ENTITY_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateWoodCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        WoodService woodService = WoodServiceImpl.getInstance();
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        CommandResult result;
        try {
            Optional<Wood> optionalWood = woodService.findById(entityId);
            if (optionalWood.isPresent()) {
                Wood wood = optionalWood.get();
                String previousName = wood.getName();
                ValidationUtil validationUtil = ValidationUtilImpl.getInstance();
                Pair<Boolean, List<String>> validationResult = validationUtil.validateWoodUpdate(name, previousName);
                if (validationResult.getLeft()) {
                    Wood updatedWood = Wood.builder().of(wood)
                            .setName(name)
                            .build();
                    woodService.update(updatedWood);
                    result = new CommandResult(ADMIN_WOODS_URL, REDIRECT);
                } else {
                    List<String> errorAttributesList = validationResult.getRight();
                    errorAttributesList.forEach(errorAttribute -> session.setAttribute(errorAttribute, true));
                    String currentEditWoodPageUrl = ADMIN_EDIT_WOOD_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    result = new CommandResult(currentEditWoodPageUrl, REDIRECT);
                }
            } else {
                logger.error("Requested wood not found, id = " + entityId);
                result = new CommandResult(SC_NOT_FOUND, ERROR);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update wood command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }
        return result;
    }
}
