package com.geekster.BloggingPlatformAPI.service;

import com.geekster.BloggingPlatformAPI.model.Comment;
import com.geekster.BloggingPlatformAPI.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class CommentService {

    @Autowired
    ICommentRepo iCommentRepo;


    public String addComment(Comment comment) {
        comment.setCommentCreationTimeStamp(LocalDateTime.now());
        iCommentRepo.save(comment);
        return "Comment has been added!!!";
    }

    public Comment findComment(Integer commentId) {
        return iCommentRepo.findById(commentId).orElse(null);
    }

    public void removeComment(Comment comment) {
        iCommentRepo.delete(comment);
    }


    public Comment findExistComment(Integer commentId) {
        return iCommentRepo.findById(commentId).orElse(null);
    }

    public void saveUpdatedComment(Comment existComment) {
        existComment.setCommentCreationTimeStamp(LocalDateTime.now());
        iCommentRepo.save(existComment);
    }
}
