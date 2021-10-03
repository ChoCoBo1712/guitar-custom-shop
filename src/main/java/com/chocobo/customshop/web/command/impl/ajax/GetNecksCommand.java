package com.chocobo.customshop.web.command.impl.ajax;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.criteria.NeckFilterCriteria;
import com.chocobo.customshop.model.service.impl.NeckServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class GetNecksCommand extends AbstractAjaxCommand<Neck> {

    private final NeckService neckService = NeckServiceImpl.getInstance();

    @Override
    Pair<Long, List<Neck>> filterForDatatables(int start, int length, String filterCriteria, String searchValue) throws ServiceException {
        return StringUtils.isEmpty(searchValue)
                ? neckService.filter(start, length, NeckFilterCriteria.NONE, null)
                : neckService.filter(start, length, NeckFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Pair<Long, List<Neck>> filterForSelect(int start, int pageSize, String filterCriteria, String searchValue) throws ServiceException {
        return neckService.filter(start, pageSize, NeckFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Optional<Neck> getFromServiceById(long entityId) throws ServiceException {
        return neckService.findById(entityId);
    }
}
