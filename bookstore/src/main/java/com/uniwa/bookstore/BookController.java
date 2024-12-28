package com.uniwa.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    // Fetch all books
    @GetMapping
    public List<Book> getAllBooks() {
        logger.debug("Fetching all books");
        List<Book> books = bookRepository.findAll();
        logger.debug("Fetched {} books", books.size());
        return books;
    }
}