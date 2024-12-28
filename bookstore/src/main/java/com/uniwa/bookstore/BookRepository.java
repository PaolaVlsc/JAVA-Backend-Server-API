package com.uniwa.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // You can add custom queries here if needed, for example, searching for books
    // by title or author.
}
