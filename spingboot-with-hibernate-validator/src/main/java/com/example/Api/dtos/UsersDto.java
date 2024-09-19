package com.example.Api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @param name
 * @param email
 * @param phoneNumber
 *
 * Using JPA Validator with record
 */
public record UsersDto(
        @NotBlank(message = "Name should not be null")
        @Size(min = 3, message = "Name should not be less than 3 alphabets")
        String name,
        @NotBlank(message = "email must not be empty")
        String email,
        String phoneNumber) {
}

