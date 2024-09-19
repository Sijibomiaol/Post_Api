package com.example.Api.services;

import com.cloudinary.Cloudinary;
import com.example.Api.dtos.UsersDto;
import com.example.Api.entities.User;
import com.example.Api.exception.CustomException;
import com.example.Api.repoository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.example.Api.constant.CloudinaryConstants.PUBLIC_ID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final Cloudinary cloudinary;
    private final UserRepository userRepository;

    @Override
    public User registerUser(UsersDto userDto, MultipartFile file)throws IOException {
        if (userRepository.findByEmail(userDto.email()) != null) {
            throw new CustomException("User with "+userDto.email() + " already exist");
        }
        String picUrl = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", PUBLIC_ID))
                .get("url").toString();
        User newUser = new User();
        newUser.setName(userDto.name());
        newUser.setEmail(userDto.email());
        newUser.setPhoneNumber(userDto.phoneNumber());
        newUser.setProfilePic(picUrl);
        return userRepository.save(newUser);
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#userID")
    /**
     TODO Investigate what is needed to be done to make @Cacheable work fine
     when a User is retrieved before creating post with the user, other attempt to
     retrieve the user will not return the user posts because the user is being retrieved from cache
     */
    public User getUser(Long userID){
        Optional<User> uniqueUser = userRepository.findById(userID);
        System.out.println("get user from DB");
        if (uniqueUser.isEmpty()){
            throw new CustomException("User with ID "+userID+ " does not exist");
        }
        return uniqueUser.get();
    }
}
