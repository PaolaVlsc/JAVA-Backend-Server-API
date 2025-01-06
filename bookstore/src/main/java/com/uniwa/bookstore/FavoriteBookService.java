package com.uniwa.bookstore;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteBookService {
    private final FavoriteBookRepository favoriteBookRepository;
    private final BookRepository bookRepository;

    public FavoriteBookService(FavoriteBookRepository favoriteBookRepository, BookRepository bookRepository) {
        this.favoriteBookRepository = favoriteBookRepository;
        this.bookRepository = bookRepository;
    }

    public List<FavoriteBookWithDetails> getAllFavorites() {
        List<FavoriteBook> favoriteBooks = favoriteBookRepository.findAll();

        // Map FavoriteBook to FavoriteBookWithDetails
        return favoriteBooks.stream().map(favoriteBook -> {
            // Fetch book details from the BookRepository
            String bookTitle = bookRepository.findById(favoriteBook.getBookId()).get().getTitle();
            String bookAuthor = bookRepository.findById(favoriteBook.getBookId()).get().getAuthor();

            return new FavoriteBookWithDetails(
                    favoriteBook.getId(),
                    favoriteBook.getUserId(),
                    favoriteBook.getBookId(),
                    bookTitle,
                    bookAuthor);
        }).collect(Collectors.toList());
    }

    public String addFavoriteBook(FavoriteBook favoriteBook) {
        FavoriteBook savedBook = favoriteBookRepository.save(favoriteBook); // Save the favorite book

        // Confirm if the book was added successfully by checking the ID
        if (savedBook != null && savedBook.getId() != null) {
            return "Book successfully added to favorites!";
        } else {
            return "Failed to add book to favorites.";
        }
    }

    public String removeFavoriteBook(Long favoriteId) {
        if (favoriteBookRepository.existsById(favoriteId)) { // Check if the favorite exists
            favoriteBookRepository.deleteById(favoriteId); // Delete the favorite
            return "Book successfully removed from favorites!";
        } else {
            return "Failed to remove book: Favorite not found.";
        }
    }

    public List<FavoriteBookWithDetails> getFavoriteBooksByUser(Long userId) {
        List<FavoriteBook> favoriteBooks = favoriteBookRepository.findByUserId(userId);

        // Map FavoriteBook to FavoriteBookWithDetails
        return favoriteBooks.stream().map(favoriteBook -> {
            // Fetch book details from the BookRepository
            String bookTitle = bookRepository.findById(favoriteBook.getBookId()).get().getTitle();
            String bookAuthor = bookRepository.findById(favoriteBook.getBookId()).get().getAuthor();

            return new FavoriteBookWithDetails(
                    favoriteBook.getId(),
                    favoriteBook.getUserId(),
                    favoriteBook.getBookId(),
                    bookTitle,
                    bookAuthor);
        }).collect(Collectors.toList());
    }
}