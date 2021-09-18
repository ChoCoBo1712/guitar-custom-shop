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
    public List<Body> filter(int start, int length, BodyFilterCriteria criteria, String keyword) throws ServiceException {
        List<Body> result;
        try {
            switch (criteria) {
                case NONE -> result = bodyDao.selectAll(start, length);
                case ID -> result = bodyDao.selectById(start, length, keyword);
                case WOOD_ID -> result = bodyDao.selectByName(start, length, keyword);
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
