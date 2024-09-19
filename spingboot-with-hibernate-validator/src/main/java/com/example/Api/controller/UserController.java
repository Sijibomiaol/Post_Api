package com.example.Api.controller;

import com.example.Api.dtos.ApiResponse;
import com.example.Api.dtos.UsersDto;
import com.example.Api.entities.User;
import com.example.Api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    @PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes ={MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<?>> createUser(@Valid @RequestPart("userDto") UsersDto userDto,
                                                     @RequestParam("image") MultipartFile image) {
        try {
            User newUser = userService.registerUser(userDto, image);
            return new ResponseEntity<>(new ApiResponse<>( "User successfully created", newUser), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getUniqueUser(@PathVariable Long id){
        try {
            var existingUser = userService.getUser(id);
            return new ResponseEntity<>(new ApiResponse<>( "USER", existingUser), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
