package com.chocobo.customshop.web.command.impl.admin.neck;

import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.web.command.Command;
import com.chocobo.customshop.web.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
import com.chocobo.customshop.model.validator.impl.NameValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.chocobo.customshop.web.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.web.command.CommandResult.RouteType.REDIRECT;
import static com.chocobo.customshop.web.command.PagePath.*;
import static com.chocobo.customshop.web.command.RequestAttribute.*;
import static com.chocobo.customshop.web.command.SessionAttribute.VALIDATION_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UpdateNeckCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final NeckService neckService = NeckServiceImpl.getInstance();
    private final Validator<String> nameValidator = NameValidator.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String name = request.getParameter(NAME);
        long woodId = Long.parseLong(request.getParameter(WOOD_ID));
        long fretboardWoodId = Long.parseLong(request.getParameter(FRETBOARD_WOOD_ID));
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));

        try {
            Optional<Neck> optionalNeck = neckService.findById(entityId);
            if (optionalNeck.isPresent()) {
                Neck neck = optionalNeck.get();
                String previousName = neck.getName();

                boolean valid = StringUtils.equals(name, previousName) || nameValidator.validate(name);

                if (valid) {
                    Neck updatedNeck = Neck.builder().of(neck)
                            .setName(name)
                            .setWoodId(woodId)
                            .setFretboardId(fretboardWoodId)
                            .build();
                    neckService.update(updatedNeck);
                    return CommandResult.createRedirectResult(ADMIN_NECKS_URL);
                } else {
                    session.setAttribute(VALIDATION_ERROR, true);
                    String currentEditPageUrl = ADMIN_EDIT_NECK_URL + AMPERSAND + ENTITY_ID + EQUALS_SIGN + entityId;
                    return CommandResult.createRedirectResult(currentEditPageUrl);
                }
            } else {
                logger.error("Requested neck not found, id = " + entityId);
                return CommandResult.createErrorResult(SC_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during update neck command execution", e);
            return CommandResult.createErrorResult(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
