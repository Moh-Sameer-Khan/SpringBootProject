package com.geekster.BloggingPlatformAPI.repository;

import com.geekster.BloggingPlatformAPI.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post, Integer> {
    List<Post> findAllByPostOwnerUserEmail(String email);
}
