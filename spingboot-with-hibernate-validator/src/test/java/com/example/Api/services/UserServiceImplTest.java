package com.example.Api.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.Api.dtos.UsersDto;
import com.example.Api.entities.User;
import com.example.Api.exception.CustomException;
import com.example.Api.repoository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;


    @Mock
    private Cloudinary cloudinary;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(cloudinary, userRepository);
    }

    @Test
    public void testRegisterUser_Success() throws IOException {
        // Mock input data
        UsersDto userDto = new UsersDto("John Doe", "john@example.com", "1234567890");
        MultipartFile file = mock(MultipartFile.class);
        byte[] fileBytes = new byte[0];
        when(file.getBytes()).thenReturn(fileBytes);

        // Mock Cloudinary response
        String publicId = "public_id";
        String picUrl = "http://res.cloudinary.com/dzrybqsds/image/upload/v1684779135/cc604850.jpg";
        Uploader uploader = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploader);
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", picUrl);
        when(uploader.upload(fileBytes, Map.of("public_id", publicId))).thenReturn(uploadResult);


        // Mock UserRepository
        when(userRepository.findByEmail(userDto.email())).thenReturn(null);
        User savedUser = new User();
        savedUser.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Call the method
        User result = userService.registerUser(userDto, file);

        // Verify the interactions and assertions
        assertNotNull(result);
        assertEquals(userDto.name(), result.getName());
        assertEquals(userDto.email(), result.getEmail());
        assertEquals(userDto.phoneNumber(), result.getPhoneNumber());
        assertEquals(picUrl, result.getProfilePic());

        verify(userRepository).findByEmail(userDto.email());
        verify(cloudinary.uploader()).upload(fileBytes, Map.of("public_id", publicId));
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testRegisterUser_DuplicateEmail() {
        // Mock input data
        UsersDto userDto = new UsersDto("John Doe", "john@example.com", "1234567890");
        MultipartFile file = mock(MultipartFile.class);

        // Mock UserRepository
        when(userRepository.findByEmail(userDto.email())).thenReturn(new User());

        // Call the method and assert the exception
        assertThrows(CustomException.class, () -> userService.registerUser(userDto, file));

        verify(userRepository).findByEmail(userDto.email());
        verifyNoInteractions(cloudinary, userRepository.save(any(User.class)));
    }
}
