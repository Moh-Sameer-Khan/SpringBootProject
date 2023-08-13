package com.geekster.BloggingPlatformAPI.service;

import com.geekster.BloggingPlatformAPI.model.Post;
import com.geekster.BloggingPlatformAPI.model.User;
import com.geekster.BloggingPlatformAPI.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    IPostRepo iPostRepo;

    public String createBlogsPost(Post post) {
//      we are taking post creation time as hide from json so, we set here creation time
        post.setPostCreatedTimeStamp(LocalDateTime.now());
        iPostRepo.save(post);
        return "Post Uploaded!!!";
    }

    public String removeBlogsPost(Integer postId, User user) {
        Post post = iPostRepo.findById(postId).orElse(null);

        if(post != null && post.getPostOwner().equals(user)) {
            iPostRepo.deleteById(postId);
            return "Post Removed successfully!!!";
        }else if (post == null) {
            return "Post to be deleted does not exist";
        }else {
            return "Un-Authorized delete detected....Not allowed";
        }

    }

    public boolean validatePost(Post blogPost) {
        return (blogPost != null && iPostRepo.existsById(blogPost.getPostId()));
    }


    public List<Post> getAllByBlogsPostAnUser(String email) {
        return iPostRepo.findAllByPostOwnerUserEmail(email);
    }

    public Post findExistPost(Integer postId) {
        return iPostRepo.findById(postId).orElse(null);
    }

    public void updatedPost(Post existPost) {
        iPostRepo.save(existPost);
    }
}
