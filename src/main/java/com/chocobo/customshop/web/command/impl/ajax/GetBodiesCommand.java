package com.chocobo.customshop.web.command.impl.ajax;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.criteria.BodyFilterCriteria;
import com.chocobo.customshop.model.service.impl.BodyServiceImpl;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class GetBodiesCommand extends AbstractAjaxCommand<Body> {

    private final BodyService bodyService = BodyServiceImpl.getInstance();

    @Override
    Pair<Long, List<Body>> filterForDatatables(int start, int length, String filterCriteria, String searchValue)
            throws ServiceException {
        return searchValue.isEmpty()
                ? bodyService.filter(start, length, BodyFilterCriteria.NONE, null)
                : bodyService.filter(start, length, BodyFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Pair<Long, List<Body>> filterForSelect(int start, int pageSize, String filterCriteria, String searchValue)
            throws ServiceException {
        return bodyService.filter(start, pageSize, BodyFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Optional<Body> getFromServiceById(long entityId) throws ServiceException {
        return bodyService.findById(entityId);
    }
}
