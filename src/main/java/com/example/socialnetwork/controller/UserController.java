package com.example.socialnetwork.controller;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String term) {
        return userService.searchUsers(term);
    }
    @GetMapping("/{id}/follow-info")
    public Map<String, Integer> getFollowInfo(@PathVariable String id) {
        User user = userService.getUserById(id);
        return Map.of(
                "followers", user.getFollowers().size(),
                "following", user.getFollowing().size()
        );
    }
}