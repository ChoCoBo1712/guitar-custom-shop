package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.entity.Neck.Tuner;
import com.chocobo.customshop.model.service.criteria.NeckFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface NeckService {

    Optional<Neck> findById(long id) throws ServiceException;

    long insert(String name, Tuner tuner, long woodId, long fretboardWoodId) throws ServiceException;

    void update(Neck neck) throws ServiceException;

    void delete(long id) throws ServiceException;

    Pair<Long, List<Neck>> filter(int start, int length, NeckFilterCriteria criteria, String keyword)
            throws ServiceException;
}
