package com.uniwa.bookstore_server.service;

import com.uniwa.bookstore_server.model.User;
import com.uniwa.bookstore_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
