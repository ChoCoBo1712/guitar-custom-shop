package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.WoodDao;
import com.chocobo.customshop.model.dao.impl.WoodDaoImpl;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;

import java.util.List;
import java.util.Optional;

public class WoodServiceImpl implements WoodService {

    private static WoodService instance;

    private final WoodDao woodDao = WoodDaoImpl.getInstance();

    public static WoodService getInstance() {
        if (instance == null) {
            instance = new WoodServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Wood> findById(long id) throws ServiceException {
        try {
            return woodDao.selectById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long insert(String name) throws ServiceException {
        Wood wood = Wood.builder()
                .setName(name)
                .build();
        try {
            return woodDao.insert(wood);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Wood wood) throws ServiceException {
        try {
            woodDao.update(wood);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            woodDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Wood> filter(int start, int length, WoodFilterCriteria criteria, String keyword) throws ServiceException {
        List<Wood> result;
        try {
            switch (criteria) {
                case NONE -> result = woodDao.selectAll(start, length);
                case ID -> result = woodDao.selectById(start, length, keyword);
                case NAME -> result = woodDao.selectByName(start, length, keyword);
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
