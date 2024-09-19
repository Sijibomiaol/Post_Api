package com.example.Api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    /**
     * Note; since I'm exposing my DTOs to the front-end the constraint such as @NotNull, @NotEmpty..
     * should be on the DTOs and not the Entity class
     */
    @NotNull(message = "Name should not be null")
    @Size(min = 3, message = "Name should not be less than 3 alphabets")
    private String name;
    private String email;
    @NotBlank(message = "phone number must not be empty")
    private String phoneNumber;
}
