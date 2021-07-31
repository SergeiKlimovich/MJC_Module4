package com.epam.esm.web.controller;

import com.epam.esm.persistence.entity.Role;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Controller that handles requests related to the User
 *
 * @author Sergei Klimovich
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends HATEOASController<UserDto> {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get List of all Users.
     *
     * @return List of UserDto objects with User data.
     */
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<UserDto> userDtoList = userService.readAll(page, size);
        addLinksToListUser(userDtoList);
        return addPagination(userDtoList, page, size, userService.getCountOfEntities());
    }

    /**
     * Get User that matches parameter userID
     *
     * @param userID The user number that we want to receive.
     * @return Object with User data.
     */
    @GetMapping("{userID}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public UserDto read(@PathVariable int userID) {
        return addLinksToUser(userService.read(userID));
    }

    /**
     * Delete user by id
     *
     * @param id of the user we want to delete
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeUser(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get user role
     *
     * @param id of the user we want to get role
     * @return user role
     */
    @GetMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserRole(@PathVariable int id) {
        Role role = userService.getUserRole(id);
        return role.name();
    }
}
