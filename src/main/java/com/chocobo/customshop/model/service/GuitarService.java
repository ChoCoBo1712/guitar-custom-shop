package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Guitar.NeckJoint;
import com.chocobo.customshop.model.service.criteria.GuitarFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.model.entity.Guitar.*;

/**
 * {@code GuitarService} interface provides functionality that allows to manipulate {@link Guitar}
 * entities without relying to exact database.
 * @author Evgeniy Sokolchik
 */
public interface GuitarService {

    /**
     * Find {@link Guitar} entity by its unique id.
     *
     * @param id unique id of guitar.
     * @return {@code Guitar} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<Guitar> findById(long id) throws ServiceException;

    /**
     * Insert values to create a new {@link Guitar} entity in the DB.
     *
     * @param name {@code Guitar} name.
     * @param picturePath {@code Guitar} picturePath.
     * @param bodyId {@code Guitar} bodyId.
     * @param neckId {@code Guitar} neckId.
     * @param pickupId {@code Guitar} pickupId.
     * @param userId {@code Guitar} userId.
     * @param color {@code Guitar} color.
     * @param neckJoint {@code Guitar} neckJoint.
     * @param status {@code Guitar} status.
     * @return {@code long} value that represents id of created entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    long insert(String name, String picturePath, long bodyId, long neckId, long pickupId, long userId, String color,
                NeckJoint neckJoint, OrderStatus status) throws ServiceException;

    /**
     * Update a {@link Guitar} entity in the DB.
     *
     * @param guitar the {@code Guitar} entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    void update(Guitar guitar) throws ServiceException;

    /**
     * Delete a {@link Guitar} entity with specified id from the DB.
     *
     * @param id unique id of the {@code Guitar} entity to delete.
     * @throws ServiceException if an error occurred executing the method.
     */
    void delete(long id) throws ServiceException;

    /**
     * Find {@link Guitar} entities that satisfy certain criteria.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param criteria instance of {@link GuitarFilterCriteria} that specifies a criteria for filtering.
     * @param keyword stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code Body} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<Guitar>> filter(int start, int length, GuitarFilterCriteria criteria, String keyword)
            throws ServiceException;

    /**
     * Find {@link Guitar} entities that satisfy certain criteria with active {@link OrderStatus}.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param criteria instance of {@link GuitarFilterCriteria} that specifies a criteria for filtering.
     * @param keyword stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code Body} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<Guitar>> filterForActiveOrder(int start, int length, GuitarFilterCriteria criteria, String keyword)
            throws ServiceException;

    /**
     * Find {@link Guitar} entities that satisfy certain criteria and have the same user id.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param keyword stored entities will be filtered by.
     * @param userId id stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code Body} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<Guitar>> filterByNameForUser(int start, int length, String keyword, String userId)
            throws ServiceException;
}
