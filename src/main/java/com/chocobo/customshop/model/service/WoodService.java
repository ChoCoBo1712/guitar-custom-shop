package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface WoodService {

    Optional<Wood> findById(long id) throws ServiceException;

    long insert(String name) throws ServiceException;

    void update(Wood wood) throws ServiceException;

    void delete(long id) throws ServiceException;

    Pair<Long, List<Wood>> filter(int start, int length, WoodFilterCriteria criteria, String keyword)
            throws ServiceException;
}
