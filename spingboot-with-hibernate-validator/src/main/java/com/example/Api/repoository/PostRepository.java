package com.example.Api.repoository;

import com.example.Api.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post, Long> {

    List<Post> findPostsByUser_Id(Long user_id);
}
