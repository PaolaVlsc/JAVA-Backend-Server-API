package com.uniwa.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniwa.bookstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
