package com.example.Api.services;

import com.example.Api.dtos.PostDto;
import com.example.Api.entities.Post;

import java.util.List;

public interface PostService {
    Post createPost(PostDto postDto, String email);

    Post getPostById(Long id);

    void deletePost(Long id);

    List<Post> getUserPost(Long id);
}
