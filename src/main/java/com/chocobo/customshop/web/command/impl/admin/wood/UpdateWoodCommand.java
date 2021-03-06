package com.chocobo.customshop.web.command.impl.admin.wood;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateWoodCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final WoodService woodService = WoodServiceImpl.getInstance();
    private final Validator<String> nameValidator = NameValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String name = request.getParameter(NAME);
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Wood> optionalWood = woodService.findById(entityId);
            if (optionalWood.isPresent()) {
                Wood wood = optionalWood.get();
                String previousName = wood.getName();

                boolean valid = StringUtils.equals(name, previousName) || nameValidator.validate(name);

                if (valid) {
                    Wood updatedWood = Wood.builder().of(wood)
                            .setName(name)
                            .build();
                    woodService.update(updatedWood);
                    return CommandResult.createRedirectResult(ADMIN_WOODS_URL);
                } else {
                    String redirectUrl = ADMIN_EDIT_WOOD_URL
                            + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId
                            + AMPERSAND + VALIDATION_ERROR + EQUALS_SIGN + true;
                    return CommandResult.createRedirectResult(redirectUrl);
                }
            } else {
                logger.error("Requested wood not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update wood command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
