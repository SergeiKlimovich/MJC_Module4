package com.epam.esm.service.service;

import com.epam.esm.persistence.entity.Role;
import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Interface provides methods to interact with UserRepository.
 *
 * @author Sergei Klimovich
 */
public interface UserService extends UserDetailsService {
    /**
     * Get User by id.
     *
     * @param id is id of user to be returned.
     * @return Object with user data.
     */
    UserDto read(int id);

    /**
     * Get List of all Users from database.
     *
     * @return List of UserDto objects with User data.
     */
    List<UserDto> readAll(int page, int size);

    /**
     * Create user in database.
     *
     * @param registrationUserDto object with registration data
     * @return Object with user data.
     */
    UserDto create(RegistrationUserDto registrationUserDto);

    /**
     * Delete user by ID
     *
     * @param id of the user we want to delete
     */
    void delete(int id);

    /**
     * Get user Role.
     *
     * @param id is id of user.
     * @return The Role of the user with the received ID
     */
    Role getUserRole(int id);

    long getCountOfEntities();

}
