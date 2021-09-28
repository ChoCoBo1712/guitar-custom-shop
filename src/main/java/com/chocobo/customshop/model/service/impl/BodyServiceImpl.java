package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.BodyDao;
import com.chocobo.customshop.model.dao.impl.BodyDaoImpl;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.BodyService;
import com.chocobo.customshop.model.service.criteria.BodyFilterCriteria;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class BodyServiceImpl implements BodyService {

    private static BodyService instance;

    private final BodyDao bodyDao = BodyDaoImpl.getInstance();

    public static BodyService getInstance() {
        if (instance == null) {
            instance = new BodyServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Body> findById(long id) throws ServiceException {
        try {
            return bodyDao.selectById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long insert(String name, long woodId) throws ServiceException {
        Body body = Body.builder()
                .setName(name)
                .setWoodId(woodId)
                .build();
        try {
            return bodyDao.insert(body);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Body body) throws ServiceException {
        try {
            bodyDao.update(body);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            bodyDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Long, List<Body>> filter(int start, int length, BodyFilterCriteria criteria, String keyword)
            throws ServiceException {
        long count;
        List<Body> resultList;
        try {
            switch (criteria) {
                case NONE -> {
                    resultList = bodyDao.selectAll(start, length);
                    count = bodyDao.selectCountAll();
                }
                case ID -> {
                    resultList = bodyDao.selectById(start, length, keyword);
                    count = bodyDao.selectCountById(keyword);
                }
                case NAME -> {
                    resultList = bodyDao.selectByName(start, length, keyword);
                    count = bodyDao.selectCountByName(keyword);
                }
                case WOOD_ID -> {
                    resultList = bodyDao.selectByWoodId(start, length, keyword);
                    count = bodyDao.selectCountByWoodId(keyword);
                }
                case NAME_AND_WOOD -> {
                    resultList = bodyDao.selectByNameAndWood(start, length, keyword);
                    count = bodyDao.selectCountByNameAndWood(keyword);
                }
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return Pair.of(count, resultList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
