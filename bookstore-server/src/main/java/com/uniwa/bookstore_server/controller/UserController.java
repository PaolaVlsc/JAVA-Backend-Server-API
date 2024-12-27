package com.uniwa.bookstore_server.controller;

import com.uniwa.bookstore_server.model.User;
import com.uniwa.bookstore_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = userService.loginUser(username, password);
        return user.isPresent() ? "Login successful" : "Invalid credentials";
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
}
