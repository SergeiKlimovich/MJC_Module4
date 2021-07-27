package com.epam.esm.service.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;

/**
 * Interface that describes CRD (create, read, delete) operations in service layer
 *
 * @author Sergei Klimovich
 */
public interface TagService {
    /**
     * Get all entities
     *
     * @return List of found entities
     */
    List<TagDto> readAll(int page, int size);

    /**
     * Get entity by id
     *
     * @param id of the entity we want to get
     * @return the entity on this id
     */
    TagDto read(final Integer id);

    /**
     * Create a new entity
     *
     * @param entity exemplar
     * @return created entity with its id
     */
    TagDto create(final TagDto entity);

    /**
     * Delete entity by id
     *
     * @param id of the entity we want to delete
     */
    void delete(final Integer id);

    /**
     * Get count of exist tags.
     *
     * @return number of exist tags.
     */
    long getCountOfEntities();

    /**
     * Get the most widely used tag from user with highest cost of all orders.
     *
     * @return TagDto
     */
    TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders();
}
