package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.BodyFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

/**
 * {@code BodyService} interface provides functionality that allows to manipulate {@link Body}
 * entities without relying to exact database.
 * @author Evgeniy Sokolchik
 */
public interface BodyService {

    /**
     * Find {@link Body} entity by its unique id.
     *
     * @param id unique id of body.
     * @return {@code Body} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<Body> findById(long id) throws ServiceException;

    /**
     * Insert values to create a new {@link Body} entity in the DB.
     *
     * @param name {@code Body} name.
     * @param woodId {@code Body} woodId.
     * @return {@code long} value that represents id of created entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    long insert(String name, long woodId) throws ServiceException;

    /**
     * Update a {@link Body} entity in the DB.
     *
     * @param body the {@code Body} entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    void update(Body body) throws ServiceException;

    /**
     * Delete a {@link Body} entity with specified id from the data store.
     *
     * @param id unique id of the {@code Body} entity to delete.
     * @throws ServiceException if an error occurred executing the method.
     */
    void delete(long id) throws ServiceException;

    /**
     * Find {@link Body} entities that satisfy certain criteria.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param criteria instance of {@link BodyFilterCriteria} that specifies a criteria for filtering.
     * @param keyword stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code Body} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<Body>> filter(int start, int length, BodyFilterCriteria criteria, String keyword)
            throws ServiceException;
}
