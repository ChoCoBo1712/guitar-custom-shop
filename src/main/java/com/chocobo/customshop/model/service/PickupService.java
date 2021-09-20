package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.PickupFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface PickupService {

    Optional<Pickup> findById(long id) throws ServiceException;

    long insert(String name) throws ServiceException;

    void update(Pickup pickup) throws ServiceException;

    void delete(long id) throws ServiceException;

    Pair<Long, List<Pickup>> filter(int start, int length, PickupFilterCriteria criteria, String keyword)
            throws ServiceException;
}
