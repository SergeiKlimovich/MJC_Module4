package com.epam.esm.service.service;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.util.CreateParameterOrder;

import java.util.List;

/**
 * Interface provides methods to interact with OrderRepository.
 *
 * @author Sergei Klimovich
 */
public interface OrderService {
    /**
     * Get List of all Orders from database.
     *
     * @return Objects with order data.
     */
    List<OrderDto> readAll(int page, int size);

    /**
     * Get Order by id from database.
     *
     * @param id order id
     * @return Object with order data.
     */
    OrderDto read(int id);

    /**
     * Get a list of all Orders by User id.
     *
     * @param userId is User id value.
     * @return List of all Orders.
     */
    List<OrderDto> readOrdersByUserID(int userId);

    /**
     * Create a new Order.
     *
     * @param createParameterOrder Data for order creation (number of a user, the certificate numbers)
     * @return Object with order data.
     */
    OrderDto create(CreateParameterOrder createParameterOrder);

    long getCountOfEntities();

    /**
     * Delete order by ID
     *
     * @param id of the order we want to delete
     */
    void delete(final int id);

}

