package com.geekster.BloggingPlatformAPI.repository;

import com.geekster.BloggingPlatformAPI.model.Follow;
import com.geekster.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface IFollowRepo extends JpaRepository<Follow, Integer> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User followTargetUser, User follower);
}
