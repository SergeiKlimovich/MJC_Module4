package com.epam.esm.web.controller;

import com.epam.esm.service.dto.AuthenticationRequestDto;
import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.service.UserService;
import com.epam.esm.web.security.JwtTokenProvider;
import com.epam.esm.web.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller that handles requests related to the authentication
 *
 * @author Sergei Klimovich
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    /**
     * Method for user authorization
     *
     * @param request login data
     */
    @PostMapping("/authorization")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtTokenProvider.createToken(request.getEmail());
        JwtUser jwtUser = (JwtUser) userService.loadUserByUsername(request.getEmail());
        Map<String, String> result = new HashMap<>();
        result.put("id",String.valueOf(jwtUser.getId()));
        result.put("name", jwtUser.getName());
        result.put("email", request.getEmail());
        result.put("user role", String.valueOf(jwtUser.getRole()));
        result.put("token", token);

        return ResponseEntity.ok(result);
    }
    /**
     * Method for authentication user
     *
     * @param registrationUserDto authentication data
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/authentication")
    public ResponseEntity register(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        UserDto newUser = userService.create(registrationUserDto);
        String token = jwtTokenProvider.createToken(registrationUserDto.getEmail());
        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(newUser.getId()));
        result.put("name", newUser.getName());
        result.put("email", newUser.getEmail());
        result.put("token", token);

        return ResponseEntity.ok(result);
    }
}
