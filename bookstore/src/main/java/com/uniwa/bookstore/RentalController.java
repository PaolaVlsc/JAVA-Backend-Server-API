package com.uniwa.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // Rent a book
    @PostMapping("/rent")
    public ResponseEntity<String> rentBook(@RequestBody Rental rentalRequest) {

        Logger logger = LoggerFactory.getLogger(RentalController.class);

        logger.debug("Renting a book");
        logger.debug("Rental request: " + rentalRequest.toString());

        // Get userId and bookId from the request body
        Long userId = rentalRequest.getUser();
        Long bookId = rentalRequest.getBook();

        // Call the service method with the userId and bookId
        Optional<Rental> rental = rentalService.rentBook(userId, bookId);

        // Check if the rental was successful
        if (rental.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book rented successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not available or invalid.");
        }
    }

    // Get all rentals
    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    // Get rental by ID
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new rental
    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        Logger logger = LoggerFactory.getLogger(RentalController.class);
        logger.debug("Creating a new rental");
        logger.debug("Rental details: " + rental.toString());

        // Set the rent date to the current time
        if (rental.getRentDate() == null) {
            rental.setRentDate(LocalDateTime.now());
        }

        // Set the due date to 14 days from rent date
        if (rental.getDueDate() == null) {
            rental.setDueDate(rental.getRentDate().toLocalDate().plusDays(14));
        }

        Rental createdRental = rentalService.createRental(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRental);
    }

    // Update an existing rental
    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @RequestBody Rental rentalDetails) {
        Optional<Rental> updatedRental = rentalService.updateRental(id, rentalDetails);
        return updatedRental.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete a rental
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        boolean isDeleted = rentalService.deleteRental(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Update rental status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Rental> updateRentalStatus(@PathVariable Long id, @RequestParam RentalStatus status) {
        Optional<Rental> rental = rentalService.updateRentalStatus(id, status);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Return a book
    @PostMapping("/return/{rentalId}")
    public ResponseEntity<String> returnBook(@PathVariable Long rentalId) {
        Logger logger = LoggerFactory.getLogger(RentalController.class);

        logger.info("Return request received for rentalId: {}", rentalId);

        try {
            // Attempt to return the book
            Optional<Rental> rental = rentalService.returnBook(rentalId);

            if (rental.isPresent()) {
                logger.info("Book returned successfully for rentalId: {}", rentalId);
                return ResponseEntity.status(HttpStatus.OK).body("Book returned successfully.");
            } else {
                logger.error("Rental record not found or already returned for rentalId: {}", rentalId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Rental record not found or book already returned.");
            }
        } catch (Exception e) {
            logger.error("Error returning book for rentalId: {}: {}", rentalId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
        }
    }

    // Get all rented books by a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rental>> getRentalsByUser(@PathVariable Long userId) {
        List<Rental> rentals = rentalService.getRentalsByUser(userId);

        if (rentals.isEmpty()) {
            return ResponseEntity.noContent().build(); // No rentals found for the user
        }

        return ResponseEntity.ok(rentals);
    }

    // Get all active rentals by a user
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Rental>> getActiveRentalsByUser(@PathVariable Long userId) {
        List<Rental> rentals = rentalService.getActiveRentalsByUser(userId);

        if (rentals.isEmpty()) {
            return ResponseEntity.noContent().build(); // No active rentals found for the user
        }

        return ResponseEntity.ok(rentals);
    }
}
