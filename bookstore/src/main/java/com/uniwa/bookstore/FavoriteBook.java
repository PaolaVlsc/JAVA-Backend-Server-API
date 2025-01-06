package com.uniwa.bookstore;

import jakarta.persistence.*;

@Entity
public class FavoriteBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long bookId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    // Constructors
    public FavoriteBook() {
    }

    public FavoriteBook(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    // toString
    @Override
    public String toString() {
        return "FavoriteBook{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}