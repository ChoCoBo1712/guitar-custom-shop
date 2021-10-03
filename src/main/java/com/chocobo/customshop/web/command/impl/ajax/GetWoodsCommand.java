package com.chocobo.customshop.web.command.impl.ajax;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class GetWoodsCommand extends AbstractAjaxCommand<Wood> {

    private final WoodService woodService = WoodServiceImpl.getInstance();

    @Override
    Pair<Long, List<Wood>> filterForDatatables(int start, int length, String filterCriteria, String searchValue) throws ServiceException {
        return StringUtils.isEmpty(searchValue)
                ? woodService.filter(start, length, WoodFilterCriteria.NONE, null)
                : woodService.filter(start, length, WoodFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Pair<Long, List<Wood>> filterForSelect(int start, int pageSize, String filterCriteria, String searchValue) throws ServiceException {
        return woodService.filter(start, pageSize, WoodFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Optional<Wood> getFromServiceById(long entityId) throws ServiceException {
        return woodService.findById(entityId);
    }
}
