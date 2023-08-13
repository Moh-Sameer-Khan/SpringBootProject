package com.geekster.BloggingPlatformAPI.service;

import com.geekster.BloggingPlatformAPI.model.Follow;
import com.geekster.BloggingPlatformAPI.model.User;
import com.geekster.BloggingPlatformAPI.repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    IFollowRepo iFollowRepo;

    public void startFollowing(Follow follow, User follower) {
        follow.setCurrentUserFollower(follower);
        iFollowRepo.save(follow);
    }

    public boolean isFollowAllowed(User followTargetUser, User follower) {
        List<Follow> followList = iFollowRepo.findByCurrentUserAndCurrentUserFollower(followTargetUser, follower);
        return followList != null && followList.isEmpty() && !followTargetUser.equals(follower);
    }

    public Follow findFollow(Integer followId) {
        return iFollowRepo.findById(followId).orElse(null);
    }

    public void unfollow(Follow follow) {
        iFollowRepo.delete(follow);
    }
}
