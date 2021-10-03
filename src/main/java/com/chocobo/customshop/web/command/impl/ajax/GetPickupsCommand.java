package com.chocobo.customshop.web.command.impl.ajax;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.criteria.PickupFilterCriteria;
import com.chocobo.customshop.model.service.impl.PickupServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class GetPickupsCommand extends AbstractAjaxCommand<Pickup> {

    private final PickupService pickupService = PickupServiceImpl.getInstance();

    @Override
    Pair<Long, List<Pickup>> filterForDatatables(int start, int length, String filterCriteria, String searchValue) throws ServiceException {
        return StringUtils.isEmpty(searchValue)
                ? pickupService.filter(start, length, PickupFilterCriteria.NONE, null)
                : pickupService.filter(start, length, PickupFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Pair<Long, List<Pickup>> filterForSelect(int start, int pageSize, String filterCriteria, String searchValue) throws ServiceException {
        return pickupService.filter(start, pageSize, PickupFilterCriteria.valueOf(filterCriteria), searchValue);
    }

    @Override
    Optional<Pickup> getFromServiceById(long entityId) throws ServiceException {
        return pickupService.findById(entityId);
    }
}
