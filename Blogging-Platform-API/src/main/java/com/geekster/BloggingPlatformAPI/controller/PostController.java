package com.geekster.BloggingPlatformAPI.controller;

import com.geekster.BloggingPlatformAPI.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class PostController {

    @Autowired
    PostService postService;
}
