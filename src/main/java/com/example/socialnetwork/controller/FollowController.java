package com.example.socialnetwork.controller;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private UserService userService;

    @PostMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<?> followUser(@PathVariable String followerId, @PathVariable String followedId) {
        userService.followUser(followerId, followedId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{followerId}/unfollow/{followedId}")
    public ResponseEntity<?> unfollowUser(@PathVariable String followerId, @PathVariable String followedId) {
        userService.unfollowUser(followerId, followedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String userId) {
        List<User> followers = userService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String userId) {
        List<User> following = userService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}