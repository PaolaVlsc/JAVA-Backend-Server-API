package com.uniwa.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

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

    // Fetch a book by ID (ID is now of type long)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) {
        logger.debug("Fetching book with ID: {}", id);

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            logger.debug("Found book: {}", book.get());
            return book.get(); // Return the found book
        } else {
            logger.error("Book not found with ID: {}", id);
            throw new BookNotFoundException(id); // Custom exception for not found books
        }
    }
}

// Custom exception to handle book not found scenario
@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("Book not found with ID: " + id);
    }
}
