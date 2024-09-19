package com.example.Api.repoository;

import com.example.Api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
