package com.geekster.BloggingPlatformAPI.service;

import com.geekster.BloggingPlatformAPI.model.*;
import com.geekster.BloggingPlatformAPI.model.dto.AuthInput;
import com.geekster.BloggingPlatformAPI.model.dto.SignInInput;
import com.geekster.BloggingPlatformAPI.model.dto.SignUpOutput;
import com.geekster.BloggingPlatformAPI.repository.IUserRepo;
import com.geekster.BloggingPlatformAPI.service.utility.emailUtility.EmailHandler;
import com.geekster.BloggingPlatformAPI.service.utility.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    public SignUpOutput signUpBlogsUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid User email!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User exitingUser = iUserRepo.findFirstByUserEmail(newEmail);
        if(exitingUser != null) {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password

        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //save  the user with the new encrypted password
            user.setUserPassword(encryptedPassword);
            iUserRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        } catch (Exception e) {
            signUpStatusMessage = "Internal error occurred during User Sign Up!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String sigInBlogsUser(SignInInput signInInput) {

        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid User email!!!";
            return signInStatusMessage;
        }

        //check if this user email already exists ??
        User existingUser = iUserRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;
        }

        //match passwords :

        //hash the password: encrypt the password

        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());

            if(existingUser.getUserPassword().equals(encryptedPassword)) {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

//                now I Want to send OTP on Email --> Email Integration
                EmailHandler.sendEMail(existingUser.getUserEmail(), "Blogs Sign-In Testing!!!", authToken.getTokenValue());;

                return "Token Sent to your Email!!!";

            }else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }

        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public void blogsUserSignOut(AuthInput authInput) {
        String newEmail = authInput.getEmail();
        User existingUser = iUserRepo.findFirstByUserEmail(newEmail);

        AuthenticationToken authToken = authenticationService.findFirstByUser(existingUser);

        authenticationService.remove(authToken);
    }

    public String createBlogsPost(Post post, String email) {
        User postOwner = iUserRepo.findFirstByUserEmail(email);
        post.setPostOwner(postOwner);
        return postService.createBlogsPost(post);
    }

    public String removeBlogsPost(Integer postId, String newEmail) {
        User user = iUserRepo.findFirstByUserEmail(newEmail);
        return postService.removeBlogsPost(postId, user);
    }

    public String addComment(Comment comment, String commenterEmail) {
        boolean postValid = postService.validatePost(comment.getBlogPost());
        if(postValid) {
            User commenter = iUserRepo.findFirstByUserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }else {
            return "Cannot comment on Invalid Post!!!";
        }
    }

    public String removeBlogsPostComment(Integer commentId, String newEmail) {
        Comment comment = commentService.findComment(commentId);
        if(comment != null) {
            if(authorizeCommentRemover(newEmail, comment)) {
                commentService.removeComment(comment);
                return "comment deleted successfully!!!";
            }else {
                return "Unauthorized delete detected...Not allowed!!!!";
            }
        }else {
            return "Invalid Comment!!!";
        }
    }

    private boolean authorizeCommentRemover(String newEmail, Comment comment) {
        String commentOwnerEmail = comment.getCommenter().getUserEmail();
        String postOwnerEmail = comment.getBlogPost().getPostOwner().getUserEmail();

        return postOwnerEmail.equals(newEmail) || commentOwnerEmail.equals(newEmail);
    }

    public String followUser(Follow follow, String followerEmail) {
        User followTargetUser = iUserRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);

        User follower = iUserRepo.findFirstByUserEmail(followerEmail);

        if(followTargetUser != null) {
            if(followService.isFollowAllowed(followTargetUser, follower)) {
                followService.startFollowing(follow, follower);
                return follower.getUserHandle() + " is now following " + followTargetUser.getUserHandle();
            }else {
                return follower.getUserHandle()  + " already follows " + followTargetUser.getUserHandle();
            }
        }else {
            return "User to be followed is Invalid!!!";
        }
    }

    public String unFollowUser(Integer followId, String followerEmail) {
        Follow follow = followService.findFollow(followId);
        if(follow != null) {
            if(authorizeUnfollow(followerEmail, follow)) {
                followService.unfollow(follow);
                return follow.getCurrentUser().getUserHandle() + " unFollowed to " + followerEmail;
            }else {
                return "Unauthorized unfollow detected...Not allowed!!!!";
            }
        }else {
            return "Invalid follow mapping!!!";
        }
    }

    private boolean authorizeUnfollow(String email, Follow follow) {
        String targetEmail = follow.getCurrentUser().getUserEmail();
        String followerEmail = follow.getCurrentUserFollower().getUserEmail();
        return targetEmail.equals(email) || followerEmail.equals(email);
    }



    public List<Post> getAllByBlogsPostAnUser(String email) {
        return postService.getAllByBlogsPostAnUser(email);
    }

    public String updatePostBlogInfo(Integer postId, Post post, String email) {
        User user = iUserRepo.findFirstByUserEmail(email);
        Post existPost = postService.findExistPost(postId);

        if(existPost != null && existPost.getPostOwner().equals(user)) {
            existPost.setPostContent(post.getPostContent());
            existPost.setPostLocation(post.getPostLocation());
            existPost.setPostCaption(post.getPostCaption());
            existPost.setPostTitle(post.getPostTitle());

            postService.updatedPost(existPost);
            return "Blog Post Information Updated Successfully!!!";
        }else {
            return "Sorry, Updated ...Not allowed for invalid information!!!!";
        }
    }

    public String updateCommentPostInfo(Integer commentId, Comment comment, String email) {
        User user = iUserRepo.findFirstByUserEmail(email);
        Comment existComment = commentService.findExistComment(commentId);
        if(existComment != null && existComment.getCommenter().equals(user)) {
            existComment.setCommentBody(comment.getCommentBody());
            commentService.saveUpdatedComment(existComment);
            return "Post Comment Information Updated Successfully!!!";
        }else {
            return "Sorry, Updated ...Not allowed for invalid information!!!!";
        }
    }
}
