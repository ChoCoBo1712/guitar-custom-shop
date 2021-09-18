package com.chocobo.customshop.controller.command.impl.admin.ajax;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.criteria.BodyFilterCriteria;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.JSON;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class GetBodiesCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter(PAGINATION_START));
        int length = Integer.parseInt(request.getParameter(PAGINATION_LENGTH));
        int draw = Integer.parseInt(request.getParameter(DRAW));
        String searchCriteria = request.getParameter(FILTER_CRITERIA);
        String searchValue = request.getParameter(SEARCH_VALUE);

        BodyService bodyService = BodyServiceImpl.getInstance();
        Map<String, Object> response = new HashMap<>();
        List<Body> bodies;

        try {
            bodies = searchValue.isEmpty()
                    ? bodyService.filter(start, length, BodyFilterCriteria.NONE, null)
                    : bodyService.filter(start, length, BodyFilterCriteria.valueOf(searchCriteria), searchValue);
        } catch (ServiceException e) {
            logger.error("An error occurred during get users command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }

        int recordsFetched = bodies.size();
        response.put(DRAW, draw);
        response.put(RECORDS_TOTAL, recordsFetched);
        response.put(RECORDS_FILTERED, recordsFetched);
        response.put(DATA, bodies);

        Gson gson = new Gson();
        return new CommandResult(gson.toJson(response), JSON);
    }
}
