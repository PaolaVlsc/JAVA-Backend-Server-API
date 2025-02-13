package com.uniwa.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uniwa.bookstore.model.Book;
import com.uniwa.bookstore.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 1. Get a list of all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // 2. Get a specific book by ID
    @GetMapping("/{bookid}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookid) {
        Optional<Book> book = bookService.getBookById(bookid);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // 3. Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    // 4. Update an existing book
    @PutMapping("/{bookid}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookid, @RequestBody Book book) {
        Optional<Book> existingBook = bookService.getBookById(bookid);
        if (existingBook.isPresent()) {
            book.setId(bookid);
            Book updatedBook = bookService.updateBook(book);
            return ResponseEntity.ok(updatedBook);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // 5. Rent a book
    @PostMapping("/rent/{bookid}")
    public ResponseEntity<String> rentBook(@PathVariable Long bookid) {
        Optional<Book> book = bookService.getBookById(bookid);
        if (book.isPresent()) {
            if (book.get().getAvailableCopies() > 0) {
                // Decrease the available copies and rent the book
                bookService.updateAvailableCopies(bookid, book.get().getAvailableCopies() - 1);
                return ResponseEntity.ok("Book rented successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No available copies left.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }

    // 6. Return a book
    @PostMapping("/return/{bookid}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookid) {
        Optional<Book> book = bookService.getBookById(bookid);
        if (book.isPresent()) {
            // Increase the available copies and return the book
            bookService.updateAvailableCopies(bookid, book.get().getAvailableCopies() + 1);
            return ResponseEntity.ok("Book returned successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }

    // 7. Get all books with available copies greater than 0
    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    // 8. Get all overdue books (Example, you might need a rental system for this)
    // This would typically involve rentals, but it’s included here as a
    // placeholder.
    @GetMapping("/overdue")
    public ResponseEntity<String> getOverdueBooks() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Overdue books feature is not yet implemented.");
    }
}
