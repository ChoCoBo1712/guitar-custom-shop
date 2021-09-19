package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.criteria.BodyFilterCriteria;

import java.util.List;
import java.util.Optional;

public interface BodyService {

    Optional<Body> findById(long id) throws ServiceException;

    long insert(String name, long woodId) throws ServiceException;

    void update(Body body) throws ServiceException;

    void delete(long id) throws ServiceException;

    List<Body> filter(int start, int length, BodyFilterCriteria criteria, String keyword) throws ServiceException;
}
