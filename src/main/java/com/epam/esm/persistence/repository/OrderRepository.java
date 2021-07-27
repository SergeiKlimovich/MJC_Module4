package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Order;

import java.util.List;

/**
 * Interface provides methods to interact with Order data.
 *
 * @author Sergei klimovich
 */
public interface OrderRepository {
    /**
     * Get Orders from database.
     *
     * @return List of found entities.
     */
    List<Order> readAll(int offset, int limit);

    /**
     * Get Order by id from database.
     *
     * @param id is Order id value.
     * @return Order entity from database.
     */
    Order read(int id);

    /**
     * Get Orders from database.
     *
     * @param userID is User id value.
     * @return List of found orders.
     */
    List<Order> readOrdersByUserID(int userID);

    /**
     * Create a new Order.
     *
     * @param order Entity with data for creating Order.
     * @return Created Order in database
     */
    Order create(Order order);

    /**
     * Get count of exist orders.
     *
     * @return number of exist orders.
     */
    long getCountOfEntities();

    /**
     * Delete order by id.
     *
     * @param id of the order we delete.
     */
    void delete(final int id);
}
