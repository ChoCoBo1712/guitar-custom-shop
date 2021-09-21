package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.service.criteria.GuitarFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface GuitarService {

    Optional<Guitar> findById(long id) throws ServiceException;

    long insert(String name, String picturePath, long bodyId, long neckId, long pickupId, long userId, String color,
                NeckJoint neckJoint) throws ServiceException;

    void update(Guitar guitar) throws ServiceException;

    void delete(long id) throws ServiceException;

    Pair<Long, List<Guitar>> filter(int start, int length, GuitarFilterCriteria criteria, String keyword)
            throws ServiceException;
}
