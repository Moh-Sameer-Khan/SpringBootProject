package com.geekster.BloggingPlatformAPI.controller;

import com.geekster.BloggingPlatformAPI.model.Comment;
import com.geekster.BloggingPlatformAPI.model.Follow;
import com.geekster.BloggingPlatformAPI.model.Post;
import com.geekster.BloggingPlatformAPI.model.User;
import com.geekster.BloggingPlatformAPI.model.dto.AuthInput;
import com.geekster.BloggingPlatformAPI.model.dto.SignInInput;
import com.geekster.BloggingPlatformAPI.model.dto.SignUpOutput;
import com.geekster.BloggingPlatformAPI.service.AuthenticationService;
import com.geekster.BloggingPlatformAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    //sign up particular Blogs user
    @PostMapping("blogs/user/signUp")
    public SignUpOutput signUpBlogsUser(@RequestBody @Valid User user)
    {
        return userService.signUpBlogsUser(user);
    }

    //Sign-In a particular Blogs user
    @PostMapping("blogs/user/signIn")
    public String sigInBlogsUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.sigInBlogsUser(signInInput);
    }

    //    User -- SIGN-OUT // DELETE
    @DeleteMapping("blogs/user/signOut")
    public String blogsUserSignOut(@RequestBody @Valid AuthInput authInput) {
        boolean authenticateValid = authenticationService.authenticateUser(authInput);
        if(authenticateValid) {
            userService.blogsUserSignOut(authInput);
            return "User Signed Out Successfully!!!";
        }else {
            return "Sign out not allowed for non authenticated user.";
        }
    }

//    Post Blogs -- POST/CREATE --> content on Blogs
    @PostMapping("blogs/post")
    public String createBlogsPost(@RequestBody @Valid Post post, @RequestParam @Valid String email, @RequestParam String tokenValue) {
//        authentication this user is sign-in, have an account
        boolean authenticateResult = authenticationService.authenticate(email, tokenValue);
        if(authenticateResult) {
//        only authorized user can post blogs
            return userService.createBlogsPost(post,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

//    delete ths post // DELETE
    @DeleteMapping("blogs/post")
    public String removeBlogsPost(@RequestParam Integer postId, @RequestBody @Valid AuthInput authInput)
    {
        String newEmail = authInput.getEmail();
        String token = authInput.getToken();
        boolean authenticateResult = authenticationService.authenticate(newEmail, token);
        if(authenticateResult) {
            return userService.removeBlogsPost(postId, newEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    //commenting functionalities on Blogs

    @PostMapping("blogs/comment")
    public String addComment(@RequestBody Comment comment, @RequestParam @Valid String commenterEmail, @RequestParam String commenterToken)
    {
        boolean authenticateResult = authenticationService.authenticate(commenterEmail, commenterToken);
        if(authenticateResult) {
            return userService.addComment(comment,commenterEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

//    Delete Comment

    @DeleteMapping("blogs/comment")
    public String removeBlogsComment(@RequestParam Integer commentId, @RequestBody @Valid AuthInput authInput)
    {
        String newEmail = authInput.getEmail();
        String newToken = authInput.getToken();

        boolean authenticateResult = authenticationService.authenticate(newEmail, newToken);
        if(authenticateResult) {
            return userService.removeBlogsPostComment(commentId,newEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }



//    Get All Post who signed user // GET
    @GetMapping("blogs/posts")
    public List<Post> getAllBlogsPostAnUser(@RequestParam @Valid String email, @RequestParam String tokenValue) {
//        authentication this user is sign-in, have an account
        boolean authenticateResult = authenticationService.authenticate(email, tokenValue);
        if(authenticateResult) {
//        only authorized user can Get All blogs Post
            return userService.getAllByBlogsPostAnUser(email);
        }
        else {
            throw  new RuntimeException("Not an Authenticated user activity!!!");
        }
    }

//    Update the post information
    @PutMapping("blogs/post/update")
    public String updatePostBlogInfo(@RequestParam @Valid String email, @RequestParam String tokenValue, @RequestParam Integer postId, @RequestBody @Valid Post post) {
//        authentication this user is sign-in, have an account
        boolean authenticateResult = authenticationService.authenticate(email, tokenValue);
        if(authenticateResult) {
//        only authorized user can Get All blogs Post
            return userService.updatePostBlogInfo(postId, post, email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @PutMapping("blogs/comment/update")
    public String updateCommentPostInfo(@RequestBody @Valid Comment comment, @RequestParam @Valid String email, @RequestParam String tokenValue, @RequestParam Integer commentId) {
//        authentication this user is sign-in, have an account
        boolean authenticateResult = authenticationService.authenticate(email, tokenValue);
        if(authenticateResult) {
//        only authorized user can Get All blogs Post
            return userService.updateCommentPostInfo(commentId, comment, email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    //follow functionality in Blogs

    @PostMapping("blogs/follow")
    public String followUser(@RequestBody @Valid Follow follow, @RequestParam @Valid String followerEmail, @RequestParam String followerToken)
    {
        boolean authenticateResult = authenticationService.authenticate(followerEmail, followerToken);
        if(authenticateResult) {
            return userService.followUser(follow, followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("blogs/unfollow/target/{followId}")
    public String unFollowUser(@PathVariable Integer followId, @RequestParam @Valid String followerEmail, @RequestParam String followerToken)
    {
        boolean authenticateResult = authenticationService.authenticate(followerEmail, followerToken);
        if(authenticateResult) {
            return userService.unFollowUser(followId,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }


}
