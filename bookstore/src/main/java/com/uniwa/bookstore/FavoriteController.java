package com.uniwa.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class FavoriteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FavoriteListRepository favoriteListRepository;

    // Add a favorite book for a user
    @PostMapping("/{username}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable String username, @RequestBody Long bookId) {
        // Find the user by username
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Find the book by ID
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        // Add the book to the user's favorites
        FavoriteList favorite = new FavoriteList(user, book);
        favoriteListRepository.save(favorite);

        return ResponseEntity.status(HttpStatus.CREATED).body("Favorite added successfully");
    }

    // Get favorite books for a user
    @GetMapping("/{username}/favorites")
    public ResponseEntity<List<Book>> getFavorites(@PathVariable String username) {
        // Find the user by username
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Retrieve the list of books from the favorite list
        List<Book> favorites = favoriteListRepository.findBooksByUserId(user.getId());
        return ResponseEntity.ok(favorites);
    }
}
