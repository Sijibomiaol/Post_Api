package com.example.Api.controller;


import com.example.Api.dtos.PostDto;
import com.example.Api.entities.Post;
import com.example.Api.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService service;


    @PostMapping("/create/{email}")
    public Post createPost (@RequestBody PostDto dto, @PathVariable String email){
        return service.createPost(dto, email);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){
        return service.getPostById(id);
    }

    @GetMapping("/user/{id}")
    public List<Post> getUserPost(@PathVariable Long id){
        return service.getUserPost(id);
    }
}
