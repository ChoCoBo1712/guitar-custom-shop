package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Neck;
import com.chocobo.customshop.model.service.criteria.NeckFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

/**
 * {@code NeckService} interface provides functionality that allows to manipulate {@link Neck}
 * entities without relying to exact database.
 * @author Evgeniy Sokolchik
 */
public interface NeckService {

    /**
     * Find {@link Neck} entity by its unique id.
     *
     * @param id unique id of neck.
     * @return {@code Neck} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<Neck> findById(long id) throws ServiceException;

    /**
     * Insert values to create a new {@link Neck} entity in the DB.
     *
     * @param name {@code Neck} name.
     * @param woodId {@code Neck} woodId.
     * @param fretboardWoodId {@code Neck} fretboardWoodId.
     * @return {@code long} value that represents id of created entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    long insert(String name, long woodId, long fretboardWoodId) throws ServiceException;

    /**
     * Update a {@link Neck} entity in the DB.
     *
     * @param neck the {@code Neck} entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    void update(Neck neck) throws ServiceException;

    /**
     * Delete a {@link Neck} entity with specified id from the data store.
     *
     * @param id unique id of the {@code Neck} entity to delete.
     * @throws ServiceException if an error occurred executing the method.
     */
    void delete(long id) throws ServiceException;

    /**
     * Find {@link Neck} entities that satisfy certain criteria.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param criteria instance of {@link NeckFilterCriteria} that specifies a criteria for filtering.
     * @param keyword stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code Neck} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<Neck>> filter(int start, int length, NeckFilterCriteria criteria, String keyword)
            throws ServiceException;
}
