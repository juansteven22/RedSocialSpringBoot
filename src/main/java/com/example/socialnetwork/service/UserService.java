package com.example.socialnetwork.service;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        // Codificar la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> searchUsers(String searchTerm) {
        return userRepository.searchUsers(searchTerm);
    }

    public void followUser(String followerId, String followedId) {
        User follower = getUserById(followerId);
        User followed = getUserById(followedId);

        if (follower != null && followed != null) {
            follower.getFollowing().add(followedId);
            followed.getFollowers().add(followerId);

            userRepository.save(follower);
            userRepository.save(followed);
        }
    }

    public void unfollowUser(String followerId, String followedId) {
        User follower = getUserById(followerId);
        User followed = getUserById(followedId);

        if (follower != null && followed != null) {
            follower.getFollowing().remove(followedId);
            followed.getFollowers().remove(followerId);

            userRepository.save(follower);
            userRepository.save(followed);
        }
    }

    public List<User> getFollowers(String userId) {
        User user = getUserById(userId);
        return user != null ? userRepository.findByIdIn(user.getFollowers()) : new ArrayList<>();
    }

    public List<User> getFollowing(String userId) {
        User user = getUserById(userId);
        return user != null ? userRepository.findByIdIn(user.getFollowing()) : new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}