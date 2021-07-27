package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides methods to interact with User data.
 *
 * @author Sergei klimovich
 */
public interface UserRepository {
    /**
     * Connects to database and returns all Users.
     *
     * @return List of all User entities.
     */
    List<User> readAll(int page, int size);

    /**
     * Connects to database and returns User by id.
     *
     * @param id is User id value.
     * @return User entity on this id.
     */
    User read(Integer id);

    /**
     * Create a new entity
     *
     * @param entity exemplar
     * @return the entity on this id
     */
    User create(User user);

    /**
     * Delete entity by id
     *
     * @param id of the entity we want to delete
     */
    void delete(Integer id);


    /**
     * Get count of exist users.
     *
     * @return number of exist users.
     */
    long getCountOfEntities();

    /**
     * Connects to database and returns User by email.
     *
     * @param email is User email value.
     * @return User entity on this email.
     */
    Optional<User> readByEmail(String email);
}
