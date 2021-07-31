package com.epam.esm.service.dto;

import lombok.Data;

@Data
public class RegistrationUserDto {
    private String name;
    private String email;
    private String password;
}
