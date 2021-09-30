package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.GuitarDao;
import com.chocobo.customshop.model.dao.impl.GuitarDaoImpl;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.service.GuitarService;
import com.chocobo.customshop.model.service.criteria.GuitarFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.model.entity.Guitar.*;

public class GuitarServiceImpl implements GuitarService {

    private static GuitarService instance;

    private final GuitarDao guitarDao = GuitarDaoImpl.getInstance();

    public static GuitarService getInstance() {
        if (instance == null) {
            instance = new GuitarServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Guitar> findById(long id) throws ServiceException {
        try {
            return guitarDao.selectById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long insert(String name, String picturePath, long bodyId, long neckId, long pickupId, long userId, String color,
                       NeckJoint neckJoint, OrderStatus orderStatus) throws ServiceException {
        Guitar guitar = builder()
                .setName(name)
                .setPicturePath(picturePath)
                .setBodyId(bodyId)
                .setNeckId(neckId)
                .setPickupId(pickupId)
                .setUserId(userId)
                .setColor(color)
                .setNeckJoint(neckJoint)
                .setOrderStatus(orderStatus)
                .build();
        try {
            return guitarDao.insert(guitar);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Guitar guitar) throws ServiceException {
        try {
            guitarDao.update(guitar);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            guitarDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Long, List<Guitar>> filter(int start, int length, GuitarFilterCriteria criteria, String keyword) throws ServiceException {
        try {
            long count;
            List<Guitar> resultList;
            switch (criteria) {
                case NONE -> {
                    resultList = guitarDao.selectAll(start, length);
                    count = guitarDao.selectCountAll();
                }
                case ID -> {
                    resultList = guitarDao.selectById(start, length, keyword);
                    count = guitarDao.selectCountById(keyword);
                }
                case NAME -> {
                    resultList = guitarDao.selectByName(start, length, keyword);
                    count = guitarDao.selectCountByName(keyword);
                }
                case COLOR -> {
                    resultList = guitarDao.selectByColor(start, length, keyword);
                    count = guitarDao.selectCountByColor(keyword);
                }
                case BODY_ID -> {
                    resultList = guitarDao.selectByBodyId(start, length, keyword);
                    count = guitarDao.selectCountByBodyId(keyword);
                }
                case NECK_ID -> {
                    resultList = guitarDao.selectByNeckId(start, length, keyword);
                    count = guitarDao.selectCountByNeckId(keyword);
                }
                case PICKUP_ID -> {
                    resultList = guitarDao.selectByPickupId(start, length, keyword);
                    count = guitarDao.selectCountByPickupId(keyword);
                }
                case USER_ID -> {
                    resultList = guitarDao.selectByUserId(start, length, keyword);
                    count = guitarDao.selectCountByUserId(keyword);
                }
                case NECK_JOINT -> {
                    resultList = guitarDao.selectByNeckJoint(start, length, keyword);
                    count = guitarDao.selectCountByNeckJoint(keyword);
                }
                case ORDER_STATUS -> {
                    resultList = guitarDao.selectByOrderStatus(start, length, keyword);
                    count = guitarDao.selectCountByOrderStatus(keyword);
                }
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return Pair.of(count, resultList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Long, List<Guitar>> filterForActiveOrder(int start, int length, GuitarFilterCriteria criteria, String keyword) throws ServiceException {
        try {
            long count;
            List<Guitar> resultList;
            switch (criteria) {
                case NONE -> {
                    resultList = guitarDao.selectAllForActiveOrder(start, length);
                    count = guitarDao.selectCountAllForActiveOrder();
                }
                case NAME -> {
                    resultList = guitarDao.selectByNameForActiveOrder(start, length, keyword);
                    count = guitarDao.selectCountByNameForActiveOrder(keyword);
                }
                case COLOR -> {
                    resultList = guitarDao.selectByColorForActiveOrder(start, length, keyword);
                    count = guitarDao.selectCountByColorForActiveOrder(keyword);
                }
                case BODY_ID -> {
                    resultList = guitarDao.selectByBodyIdForActiveOrder(start, length, keyword);
                    count = guitarDao.selectCountByBodyIdForActiveOrder(keyword);
                }
                case NECK_ID -> {
                    resultList = guitarDao.selectByNeckIdForActiveOrder(start, length, keyword);
                    count = guitarDao.selectCountByNeckIdForActiveOrder(keyword);
                }
                case PICKUP_ID -> {
                    resultList = guitarDao.selectByPickupIdForActiveOrder(start, length, keyword);
                    count = guitarDao.selectCountByPickupIdForActiveOrder(keyword);
                }
                case USER_ID -> {
                    resultList = guitarDao.selectByUserIdForActiveOrder(start, length, keyword);
                    count = guitarDao.selectCountByUserIdForActiveOrder(keyword);
                }
                case NECK_JOINT -> {
                    resultList = guitarDao.selectByNeckJointForActiveOrder(start, length, keyword);
                    count = guitarDao.selectCountByNeckJointForActiveOrder(keyword);
                }
                case ORDER_STATUS -> {
                    resultList = guitarDao.selectByOrderStatus(start, length, keyword);
                    count = guitarDao.selectCountByOrderStatus(keyword);
                }
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return Pair.of(count, resultList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Long, List<Guitar>> filterByNameForUser(int start, int length, String keyword, String userId) throws ServiceException {
        try {
            List<Guitar> resultList = guitarDao.selectByNameForUser(start, length, keyword, userId);
            long count = guitarDao.selectCountByNameForUser(keyword, userId);
            return Pair.of(count, resultList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
