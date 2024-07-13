package com.example.socialnetwork.controller;

import com.example.socialnetwork.model.Post;
import com.example.socialnetwork.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable String userId) {
        return postService.getPostsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }
}