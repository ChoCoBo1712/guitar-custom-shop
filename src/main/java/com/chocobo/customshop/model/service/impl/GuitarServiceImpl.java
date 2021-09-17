package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.model.dao.GuitarDao;
import com.chocobo.customshop.model.dao.impl.GuitarDaoImpl;
import com.chocobo.customshop.model.service.GuitarService;

public class GuitarServiceImpl implements GuitarService {

    private static GuitarService instance;

    private final GuitarDao guitarDao = GuitarDaoImpl.getInstance();

    public static GuitarService getInstance() {
        if (instance == null) {
            instance = new GuitarServiceImpl();
        }
        return instance;
    }
}
