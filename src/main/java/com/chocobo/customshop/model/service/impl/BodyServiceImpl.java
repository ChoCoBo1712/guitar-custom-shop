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
    public List<Body> filter(int start, int length, BodyFilterCriteria criteria, String keyword) throws ServiceException {
        List<Body> result;
        try {
            switch (criteria) {
                case NONE -> result = bodyDao.selectAll(start, length);
                case ID -> result = bodyDao.selectById(start, length, keyword);
                case ID_WOOD -> result = bodyDao.selectByWoodId(start, length, keyword);
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
