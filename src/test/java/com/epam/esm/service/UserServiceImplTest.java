package com.epam.esm.service;

import com.epam.esm.persistence.entity.User;
import com.epam.esm.persistence.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.NotExistEntityException;
import com.epam.esm.service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepositoryImpl userRepositoryImpl;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDto userDto;
    private List<User> userList;
    private List<UserDto> userDtoList;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setName("user");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("user");
        userList = new ArrayList<>();
        userDtoList = new ArrayList<>();
        userList.add(user);
        userDtoList.add(userDto);
    }

    @Test
    @DisplayName("Getting a list of UsersDto.")
    public void readAll_returnsTheExpectedResult_test() {
        //when
        when(userRepositoryImpl.getCountOfEntities()).thenReturn(1L);
        when(userRepositoryImpl.readAll(0, 4)).thenReturn(userList);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        //then
        assertEquals(userDtoList, userServiceImpl.readAll(1, 4));
    }

    @Test
    @DisplayName("Getting a list of users.")
    public void getCountOfEntities_returnsTheExpectedResult_test() {
        //when
        when(userRepositoryImpl.getCountOfEntities()).thenReturn(1L);
        //then
        assertEquals(1L, userServiceImpl.getCountOfEntities());
    }
}
