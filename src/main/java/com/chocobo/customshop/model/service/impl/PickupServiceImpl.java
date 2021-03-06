package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.PickupDao;
import com.chocobo.customshop.model.dao.impl.PickupDaoImpl;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.PickupService;
import com.chocobo.customshop.model.service.criteria.PickupFilterCriteria;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class PickupServiceImpl implements PickupService {

    private static PickupService instance;

    private final PickupDao pickupDao = PickupDaoImpl.getInstance();

    public static PickupService getInstance() {
        if (instance == null) {
            instance = new PickupServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Pickup> findById(long id) throws ServiceException {
        try {
            return pickupDao.selectById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long insert(String name) throws ServiceException {
        Pickup pickup = Pickup.builder()
                .setName(name)
                .build();
        try {
            return pickupDao.insert(pickup);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Pickup pickup) throws ServiceException {
        try {
            pickupDao.update(pickup);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            pickupDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Long, List<Pickup>> filter(int start, int length, PickupFilterCriteria criteria, String keyword)
            throws ServiceException {
        long count;
        List<Pickup> resultList;
        try {
            switch (criteria) {
                case NONE -> {
                    resultList = pickupDao.selectAll(start, length);
                    count = pickupDao.selectCountAll();
                }
                case ID -> {
                    resultList = pickupDao.selectById(start, length, keyword);
                    count = pickupDao.selectCountById(keyword);
                }
                case NAME -> {
                    resultList = pickupDao.selectByName(start, length, keyword);
                    count = pickupDao.selectCountByName(keyword);
                }
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return Pair.of(count, resultList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
