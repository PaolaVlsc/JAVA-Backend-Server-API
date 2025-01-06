package com.uniwa.bookstore;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteBookController {

    private final FavoriteBookService service;

    public FavoriteBookController(FavoriteBookService service) {
        this.service = service;
    }

    /**
     * Add a book to the user's favorite list
     * 
     * @param favoriteBook The favorite book object to add
     * @return Response with a success message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addFavoriteBook(@RequestBody FavoriteBook favoriteBook) {
        try {
            String message = service.addFavoriteBook(favoriteBook);
            return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 for successful creation
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding favorite book: " + e.getMessage());
        }
    }

    /**
     * Remove a book from the user's favorite list
     * 
     * @param favoriteId The ID of the favorite book to remove
     * @return Response with a success message
     */
    @DeleteMapping("/remove/{favoriteId}")
    public ResponseEntity<String> removeFavoriteBook(@PathVariable Long favoriteId) {
        try {
            String message = service.removeFavoriteBook(favoriteId);
            return ResponseEntity.ok(message); // Return 200 OK for successful removal
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite book not found: " + e.getMessage());
        }
    }

    /**
     * Get all favorite books for a user, including book details (e.g., title,
     * author)
     * 
     * @param userId The ID of the user whose favorites are to be retrieved
     * @return List of favorite books with details
     */
    @GetMapping("/view/{userId}")
    public ResponseEntity<List<FavoriteBookWithDetails>> getFavorites(@PathVariable Long userId) {
        try {
            List<FavoriteBookWithDetails> favorites = service.getFavoriteBooksByUser(userId);
            if (favorites.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 if no favorites found
            }
            return ResponseEntity.ok(favorites); // Return 200 OK with the list of favorite books
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if user not found or error
                                                                           // occurs
        }
    }
}
