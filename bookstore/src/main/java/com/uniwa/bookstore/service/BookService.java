package com.uniwa.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniwa.bookstore.model.Book;
import com.uniwa.bookstore.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    // Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Update an existing book
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    // Update available copies of a book
    public void updateAvailableCopies(Long bookId, int availableCopies) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Book updatedBook = book.get();
            updatedBook.setAvailableCopies(availableCopies);
            bookRepository.save(updatedBook);
        }
    }

    // Get all books with available copies > 0
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }
}
