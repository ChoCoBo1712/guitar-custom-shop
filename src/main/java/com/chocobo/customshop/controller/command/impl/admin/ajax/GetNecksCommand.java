package com.chocobo.customshop.controller.command.impl.admin.ajax;

import com.chocobo.customshop.controller.command.Command;
import com.chocobo.customshop.controller.command.CommandResult;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.criteria.NeckFilterCriteria;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.chocobo.customshop.controller.command.CommandResult.RouteType.ERROR;
import static com.chocobo.customshop.controller.command.CommandResult.RouteType.JSON;
import static com.chocobo.customshop.controller.command.RequestAttribute.*;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class GetNecksCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private NeckService neckService;

    @Override
    public CommandResult execute(HttpServletRequest request) {
        neckService = NeckServiceImpl.getInstance();
        String requestTypeParameter = request.getParameter(REQUEST_TYPE);
        AjaxRequestType requestType = AjaxRequestType.valueOf(requestTypeParameter);
        Map<String, Object> responseMap = new HashMap<>();

        try {
            switch (requestType) {
                case DATATABLE -> processDatatablesRequest(request, responseMap);
                case FETCH -> processFetchRequest(request, responseMap);
                case SELECT -> processSelectRequest(request, responseMap);
                default -> throw new ServiceException("Invalid request type: " + requestType);
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during get necks command execution", e);
            return new CommandResult(SC_INTERNAL_SERVER_ERROR, ERROR);
        }

        Gson gson = new Gson();
        return new CommandResult(gson.toJson(responseMap), JSON);
    }

    private void processDatatablesRequest(HttpServletRequest request, Map<String, Object> responseMap)
            throws ServiceException {
        int start = Integer.parseInt(request.getParameter(PAGINATION_START));
        int length = Integer.parseInt(request.getParameter(PAGINATION_LENGTH));
        int draw = Integer.parseInt(request.getParameter(DRAW));
        String searchCriteria = request.getParameter(FILTER_CRITERIA);
        String searchValue = request.getParameter(SEARCH_VALUE);

        Pair<Long, List<Neck>> pair = searchValue.isEmpty()
                ? neckService.filter(start, length, NeckFilterCriteria.NONE, null)
                : neckService.filter(start, length, NeckFilterCriteria.valueOf(searchCriteria), searchValue);

        long recordsFetched = pair.getLeft();
        List<Neck> necks = pair.getRight();
        responseMap.put(DRAW, draw);
        responseMap.put(RECORDS_TOTAL, recordsFetched);
        responseMap.put(RECORDS_FILTERED, recordsFetched);
        responseMap.put(DATA, necks);
    }

    private void processFetchRequest(HttpServletRequest request, Map<String, Object> responseMap)
            throws ServiceException {
        long entityId = Long.parseLong(request.getParameter(ENTITY_ID));
        Optional<Neck> neck = neckService.findById(entityId);
        neck.ifPresent(value -> responseMap.put(ENTITY, value));
    }

    private void processSelectRequest(HttpServletRequest request, Map<String, Object> responseMap)
            throws ServiceException {
        String searchValue = request.getParameter(TERM);
        int page = Integer.parseInt(request.getParameter(PAGE));
        int pageSize = Integer.parseInt(request.getParameter(PAGE_SIZE));
        int start = pageSize * (page - 1);
        String filterCriteria = request.getParameter(FILTER_CRITERIA);

        Pair<Long, List<Neck>> pair = neckService.filter(start, pageSize, NeckFilterCriteria.valueOf(filterCriteria), searchValue);
        long recordsFetched = pair.getLeft();
        List<Neck> necks = pair.getRight();
        responseMap.put(RESULTS, necks);
        responseMap.put(PAGINATION_MORE, (long) page * pageSize < recordsFetched);
    }
}
