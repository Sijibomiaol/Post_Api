package com.example.Api.services;

import com.example.Api.dtos.PostDto;
import com.example.Api.entities.Post;
import com.example.Api.entities.User;
import com.example.Api.exception.CustomException;
import com.example.Api.repoository.PostRepository;
import com.example.Api.repoository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public Post createPost(PostDto postDto, String email) {
        User exisitingUser = userRepository.findByEmail(email);
        if (exisitingUser == null){
            throw new CustomException("User with the "+email+ " does not exist");
        }
        Post newPost = new Post();
        newPost.setName(postDto.getName());
        newPost.setTitle(postDto.getTitle());
        newPost.setDescription(postDto.getDescription());
        newPost.setUser(exisitingUser);
        return postRepository.save(newPost);

    }
    @Override
    @Cacheable(cacheNames = "post", key = "#id")
    public Post getPostById(Long id){
        Optional<Post> pst = postRepository.findById(id);
        if(pst.isEmpty()){
            throw new CustomException("Post with "+id+" does not exist");
        }
        return pst.get();
    }

    @Override
    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new CustomException("Post with ID: "+id+" does not exist");
        }
        postRepository.delete(post.get());
    }

    @Override
    public List<Post> getUserPost(Long id){
        var userPost = postRepository.findPostsByUser_Id(id);
        if(userPost.isEmpty()){
            return new ArrayList<>();
        }
        userPost.sort(Comparator.comparing(Post::getId).reversed());
        return userPost;
    }
}
