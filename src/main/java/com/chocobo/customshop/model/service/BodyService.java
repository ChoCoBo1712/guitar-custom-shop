package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;

import java.util.List;

public interface BodyService {

    List<Body> filter(int start, int length, UserFilterCriteria criteria, String keyword) throws ServiceException;
}
