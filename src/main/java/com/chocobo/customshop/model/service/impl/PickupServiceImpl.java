package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.model.dao.PickupDao;
import com.chocobo.customshop.model.dao.impl.PickupDaoImpl;
import com.chocobo.customshop.model.service.PickupService;

public class PickupServiceImpl implements PickupService {

    private static PickupService instance;

    private final PickupDao pickupDao = PickupDaoImpl.getInstance();

    public static PickupService getInstance() {
        if (instance == null) {
            instance = new PickupServiceImpl();
        }
        return instance;
    }
}
