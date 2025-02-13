package com.uniwa.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uniwa.bookstore.model.LoginResponse;
import com.uniwa.bookstore.model.User;
import com.uniwa.bookstore.repository.UserRepository;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        logger.debug("Login request received for username: {}", user.getUsername());

        // Find the user by username
        User foundUser = userRepository.findByUsername(user.getUsername());

        // Check if the user exists and password matches
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            logger.debug("Login successful for username: {}", user.getUsername());

            // Create a LoginResponse object with the success message
            LoginResponse loginResponse = new LoginResponse(foundUser.getId(), foundUser.getUsername(),
                    "Login successful");
            return ResponseEntity.ok(loginResponse);
        } else {
            logger.debug("Invalid credentials for username: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        logger.debug("Register request received for username: {}", user.getUsername());

        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            logger.debug("Username already exists: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Save the new user to the database
        userRepository.save(user);
        logger.debug("User registered successfully: {}", user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}
