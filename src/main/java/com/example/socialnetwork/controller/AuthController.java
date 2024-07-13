package com.example.socialnetwork.controller;

import com.example.socialnetwork.config.JwtUtil;
import com.example.socialnetwork.model.User;
import com.example.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User registeredUser = userService.createUser(user);
        return ResponseEntity.ok(registeredUser);
    }
/*
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        final UserDetails userDetails = userService.getUserByUsername(user.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }*/
@PostMapping("/login")
public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
    );
    final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthResponse(jwt));
}
}

class AuthResponse {
    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}