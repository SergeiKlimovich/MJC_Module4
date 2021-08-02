package com.epam.esm.service.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data

public class RegistrationUserDto {

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 45, message = "Name should be between 2 and 45 characters")
    private String name;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 5, max = 255, message = "Password should be between 5 and 255 characters")
    private String password;
}
