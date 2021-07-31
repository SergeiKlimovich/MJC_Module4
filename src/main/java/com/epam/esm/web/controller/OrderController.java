package com.epam.esm.web.controller;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.util.CreateParameterOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Controller that handles requests related to the order
 *
 * @author Sergei Klimovich
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController extends HATEOASController<OrderDto> {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Invokes service method to get List of all Orders.
     *
     * @return List of {@link OrderDto} objects with Order data.
     */
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<OrderDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<OrderDto> orderDtoList = orderService.readAll(page, size);
        addLinksToListOrder(orderDtoList);
        return addPagination(orderDtoList, page, size, orderService.getCountOfEntities());
    }

    /**
     * Get List of all Orders that matches parameter userID
     *
     * @param userID user id we want to view the list of his orders
     * @return List of {@link OrderDto} objects with Order data.
     */
    @GetMapping(params = "user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<OrderDto> readByUserId(@RequestParam(value = "user") int userID) {
        List<OrderDto> orderDtoList = orderService.readOrdersByUserID(userID);
        addLinksToListOrder(orderDtoList);
        return orderDtoList;
    }

    /**
     * Get Order that matches parameter orderID
     *
     * @param orderID The order number that we want to receive.
     * @return Object with Order data.
     */
    @GetMapping("{orderID}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto read(@PathVariable int orderID) {
        return addLinksToOrder(orderService.read(orderID));
    }

    /**
     * Create Order
     *
     * @param createParameterOrder Object containing data for creating an order.
     * @return Created Order.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public OrderDto create(@Valid @RequestBody CreateParameterOrder createParameterOrder) {
        return orderService.create(createParameterOrder);
    }

    /**
     * Delete order by ID
     *
     * @param id of the order we delete
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/order/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeOrder(@PathVariable int id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
