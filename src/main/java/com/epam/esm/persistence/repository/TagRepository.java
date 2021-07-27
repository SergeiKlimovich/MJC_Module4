package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Tag;

import java.util.List;
import java.util.Optional;


/**
 * An interface that inherits the CRD and other the methods required for the tag
 *
 * @author Sergei Klimovich
 */
public interface TagRepository {
    /**
     * Get all entities from database
     *
     * @return List of found entities
     */
    List<Tag> readAll(int offset, int limit);

    /**
     * Get entity
     *
     * @param id of the entity we want to get from database
     * @return the entity on this id
     */
    Tag read(final Integer id);

    /**
     * Create a new entity
     *
     * @param entity exemplar
     * @return the entity on this id
     */
    Tag create(final Tag entity);

    /**
     * Delete entity by id
     *
     * @param id of the entity we want to delete
     */
    void delete(final Integer id);

    /**
     * Get entity
     *
     * @param tagName of the entity we want to get from database
     * @return the entity with this tagName
     */
    Optional<Tag> readTagByName(String tagName);

    /**
     * Get count of exist tags.
     *
     * @return number of exist tags
     */
    long getCountOfEntities();

    /**
     * Get the most widely used tag from user with highest cost of all orders.
     *
     * @return Tag entity
     */
    Tag getMostWidelyUsedTagFromUser();

}
