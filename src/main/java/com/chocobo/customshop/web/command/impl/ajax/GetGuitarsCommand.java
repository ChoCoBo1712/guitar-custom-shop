package com.chocobo.customshop.web.command.impl.ajax;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.criteria.GuitarFilterCriteria;
import com.chocobo.customshop.model.service.impl.GuitarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.chocobo.customshop.web.command.RequestAttribute.*;

public class GetGuitarsCommand extends AbstractAjaxCommand<Guitar> {

    private final GuitarService guitarService = GuitarServiceImpl.getInstance();
    ;

    @Override
    Pair<Long, List<Guitar>> filterForDatatables(int start, int length, String filterCriteria, String searchValue)
            throws ServiceException {
        return StringUtils.isEmpty(searchValue)
                ? guitarService.filter(start, length, GuitarFilterCriteria.NONE, null)
                : guitarService.filter(start, length, GuitarFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Pair<Long, List<Guitar>> filterForSelect(int start, int pageSize, String userId, String searchValue)
            throws ServiceException {
        return guitarService.filterByNameForUser(start, pageSize, searchValue, userId);
    }

    @Override
    Optional<Guitar> getFromServiceById(long entityId) throws ServiceException {
        return guitarService.findById(entityId);
    }

    @Override
    protected void processDatatablesRequest(HttpServletRequest request, Map<String, Object> responseMap)
            throws ServiceException {
        boolean activeOrder = Boolean.parseBoolean(request.getParameter(ACTIVE_ORDER));
        int start = Integer.parseInt(request.getParameter(PAGINATION_START));
        int length = Integer.parseInt(request.getParameter(PAGINATION_LENGTH));
        int draw = Integer.parseInt(request.getParameter(DRAW));
        String filterCriteria = request.getParameter(FILTER_CRITERIA);
        String searchValue = request.getParameter(SEARCH_VALUE);

        Pair<Long, List<Guitar>> pair;
        if (activeOrder) {
            pair = filterForActiveOrderForDatatables(start, length, filterCriteria, searchValue);
        } else {
            pair = filterForDatatables(start, length, filterCriteria, searchValue);
        }

        long recordsFetched = pair.getLeft();
        List<Guitar> guitars = pair.getRight();

        responseMap.put(DRAW, draw);
        responseMap.put(RECORDS_TOTAL, recordsFetched);
        responseMap.put(RECORDS_FILTERED, recordsFetched);
        responseMap.put(DATA, guitars);
    }

    private Pair<Long, List<Guitar>> filterForActiveOrderForDatatables(int start, int length, String filterCriteria,
                                                                       String searchValue) throws ServiceException {
        return searchValue.isEmpty()
                ? guitarService.filterForActiveOrder(start, length, GuitarFilterCriteria.NONE, null)
                : guitarService.filterForActiveOrder(start, length, GuitarFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    protected void processSelectRequest(HttpServletRequest request, Map<String, Object> responseMap) throws ServiceException {
        String searchValue = request.getParameter(TERM);
        String userId = String.valueOf(request.getSession().getAttribute(USER_ID));
        int page = Integer.parseInt(request.getParameter(PAGE));
        int pageSize = Integer.parseInt(request.getParameter(PAGE_SIZE));
        int start = pageSize * (page - 1);

        Pair<Long, List<Guitar>> pair = filterForSelect(start, pageSize, userId, searchValue);
        long recordsFetched = pair.getLeft();
        List<Guitar> guitars = pair.getRight();
        responseMap.put(RESULTS, guitars);
        responseMap.put(PAGINATION_MORE, (long) page * pageSize < recordsFetched);
    }
}
