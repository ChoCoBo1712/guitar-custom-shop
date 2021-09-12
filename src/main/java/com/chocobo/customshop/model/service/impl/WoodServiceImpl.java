package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.WoodDao;
import com.chocobo.customshop.model.dao.impl.WoodDaoImpl;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;

import java.util.ArrayList;
import java.util.List;

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
    public List<Wood> filter(int start, int length, WoodFilterCriteria criteria, String keyword) throws ServiceException {
        List<Wood> result = new ArrayList<>();
        try {
            switch (criteria) {
                case NONE -> result = woodDao.selectAll(start, length);
                case ID -> {
                }
                case NAME -> {
                }
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
