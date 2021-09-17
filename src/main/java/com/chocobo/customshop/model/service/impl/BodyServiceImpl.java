package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.model.dao.BodyDao;
import com.chocobo.customshop.model.dao.impl.BodyDaoImpl;
import com.chocobo.customshop.model.service.BodyService;

public class BodyServiceImpl implements BodyService {

    private static BodyService instance;

    private final BodyDao bodyDao = BodyDaoImpl.getInstance();

    public static BodyService getInstance() {
        if (instance == null) {
            instance = new BodyServiceImpl();
        }
        return instance;
    }
}
