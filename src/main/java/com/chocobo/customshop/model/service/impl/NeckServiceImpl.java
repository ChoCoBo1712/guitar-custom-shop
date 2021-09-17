package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.model.dao.NeckDao;
import com.chocobo.customshop.model.dao.impl.NeckDaoImpl;
import com.chocobo.customshop.model.service.NeckService;

public class NeckServiceImpl implements NeckService {

    private static NeckService instance;

    private final NeckDao neckDao = NeckDaoImpl.getInstance();

    public static NeckService getInstance() {
        if (instance == null) {
            instance = new NeckServiceImpl();
        }
        return instance;
    }
}
