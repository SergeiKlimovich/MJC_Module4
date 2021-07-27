package com.epam.esm.service;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Order;
import com.epam.esm.persistence.entity.User;
import com.epam.esm.persistence.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.persistence.repository.impl.OrderRepositoryImpl;
import com.epam.esm.persistence.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.NotExistEntityException;
import com.epam.esm.service.service.impl.OrderServiceImpl;
import com.epam.esm.service.util.CreateParameterOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Mock
    private UserRepositoryImpl userRepositoryImpl;

    @Mock
    private OrderRepositoryImpl orderRepositoryImpl;

    @Mock
    private GiftCertificateRepositoryImpl giftCertificateRepositoryImpl;

    @Mock
    private ModelMapper modelMapper;

    private static final Integer EXISTING_ID = 1;
    private static final Integer NON_EXISTING_ID = 1111111111;
    private User user;
    private UserDto userDto;
    private Order order;
    private OrderDto orderDto;
    private List<Order> orderList;
    private List<OrderDto> orderDtoList;
    private LocalDateTime orderDate;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(EXISTING_ID);
        user.setName("user");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("user");

        orderDate = LocalDateTime.now(ZoneId.systemDefault());

        order = new Order();
        order.setId(EXISTING_ID);
        order.setPrice(new Double(1));
        order.setUser(user);
        order.setDate(orderDate);

        orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setDate(orderDate);
        orderDto.setPrice(new Double(1));
        orderDto.setUser(userDto);

        orderList = new ArrayList<>();
        orderList.add(order);

        orderDtoList = new ArrayList<>();
        orderDtoList.add(orderDto);

    }

    @Test
    @DisplayName("Getting the list of OrderDto")
    void readAll_returnsTheExpectedResult_test() {
        //given
        long count = 1L;
        //when
        when(orderRepositoryImpl.getCountOfEntities()).thenReturn(count);
        when(orderRepositoryImpl.readAll(0, 4)).thenReturn(orderList);
        when(modelMapper.map(order, OrderDto.class)).thenReturn(orderDto);
        //then
        assertEquals(orderDtoList, orderServiceImpl.readAll(1, 4));
    }

    @Test
    @DisplayName("The method should return the OrderDto")
    void read_returnsTheExpectedResult_test() {
        //when
        when(orderRepositoryImpl.read(EXISTING_ID)).thenReturn(order);
        when(modelMapper.map(order, OrderDto.class)).thenReturn(orderDto);
        //then
        assertEquals(orderDto, orderServiceImpl.read(EXISTING_ID));
    }

    @Test
    @DisplayName("A Order with such an ID does not exist, an appropriate exception must be thrown")
    void read_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(orderRepositoryImpl.read(NON_EXISTING_ID)).thenReturn(null);
        //then
        assertThrows(NotExistEntityException.class, () -> orderServiceImpl.read(NON_EXISTING_ID));
    }

    @Test
    @DisplayName("Getting the number of existing orders")
    void getCountOfEntities_returnsTheExpectedResult_test() {
        long count = 2L;
        //when
        when(orderRepositoryImpl.getCountOfEntities()).thenReturn(count);
        //then
        assertEquals(count, orderRepositoryImpl.getCountOfEntities());
    }
}
