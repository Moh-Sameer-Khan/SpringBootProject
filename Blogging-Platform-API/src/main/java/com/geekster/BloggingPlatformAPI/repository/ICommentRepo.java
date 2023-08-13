package com.geekster.BloggingPlatformAPI.repository;

import com.geekster.BloggingPlatformAPI.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICommentRepo extends JpaRepository<Comment, Integer> {

}
