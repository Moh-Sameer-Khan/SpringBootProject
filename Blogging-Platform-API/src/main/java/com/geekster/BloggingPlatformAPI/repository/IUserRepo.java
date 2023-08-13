package com.geekster.BloggingPlatformAPI.repository;

import com.geekster.BloggingPlatformAPI.model.AuthenticationToken;
import com.geekster.BloggingPlatformAPI.model.Post;
import com.geekster.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {
    User findFirstByUserEmail(String newEmail);

    List<Post> findAllByUserEmail(String email);
}
