package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;

import java.util.List;

public interface WoodService {

    List<Wood> filter(int start, int length, WoodFilterCriteria criteria, String keyword) throws ServiceException;
}
