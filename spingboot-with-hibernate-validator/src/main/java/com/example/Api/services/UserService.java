package com.example.Api.services;

import com.example.Api.dtos.PostDto;
import com.example.Api.dtos.UsersDto;
import com.example.Api.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    User registerUser(UsersDto userDto, MultipartFile file)throws Exception;

    User getUser(Long userID);

//    ResponseEntity<?> creeatePost(PostDto dto, String userEmail);
}
