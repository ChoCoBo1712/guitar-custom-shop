package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.NeckDao;
import com.chocobo.customshop.model.dao.impl.NeckDaoImpl;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.entity.Neck.Tuner;
import com.chocobo.customshop.model.service.NeckService;
import com.chocobo.customshop.model.service.criteria.BodyFilterCriteria;
import com.chocobo.customshop.model.service.criteria.NeckFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class NeckServiceImpl implements NeckService {

    private static NeckService instance;

    private final NeckDao neckDao = NeckDaoImpl.getInstance();

    public static NeckService getInstance() {
        if (instance == null) {
            instance = new NeckServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Neck> findById(long id) throws ServiceException {
        try {
            return neckDao.selectById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long insert(String name, Tuner tuner, long woodId, long fretboardWoodId) throws ServiceException {
        Neck neck = Neck.builder()
                .setName(name)
                .setTuner(tuner)
                .setWoodId(woodId)
                .setFretboardId(fretboardWoodId)
                .build();
        try {
            return neckDao.insert(neck);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Neck neck) throws ServiceException {
        try {
            neckDao.update(neck);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            neckDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Long, List<Neck>> filter(int start, int length, NeckFilterCriteria criteria, String keyword) throws ServiceException {
        long count;
        List<Neck> resultList;
        try {
            switch (criteria) {
                case NONE -> {
                    resultList = neckDao.selectAll(start, length);
                    count = neckDao.selectCountAll();
                }
                case ID -> {
                    resultList = neckDao.selectById(start, length, keyword);
                    count = neckDao.selectCountById(keyword);
                }
                case NAME -> {
                    resultList = neckDao.selectByName(start, length, keyword);
                    count = neckDao.selectCountByName(keyword);
                }
                case TUNER -> {
                    resultList = neckDao.selectByTuner(start, length, keyword);
                    count = neckDao.selectCountByTuner(keyword);
                }
                case WOOD_ID -> {
                    resultList = neckDao.selectByWoodId(start, length, keyword);
                    count = neckDao.selectCountByWoodId(keyword);
                }
                case FRETBOARD_WOOD_ID -> {
                    resultList = neckDao.selectByFretboardWoodId(start, length, keyword);
                    count = neckDao.selectCountByFretboardWoodId(keyword);
                }
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return Pair.of(count, resultList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
