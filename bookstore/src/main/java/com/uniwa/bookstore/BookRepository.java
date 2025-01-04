package com.uniwa.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Custom query to get books with available copies greater than 0
    List<Book> findByAvailableCopiesGreaterThan(int availableCopies);

    // Optional<Book> findById(Long id); // Fetch book by id
    Optional<Book> findById(Long id); // Fetch book by id

}
