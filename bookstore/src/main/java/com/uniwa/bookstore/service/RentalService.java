package com.uniwa.bookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniwa.bookstore.controller.RentalController;
import com.uniwa.bookstore.model.Book;
import com.uniwa.bookstore.model.Rental;
import com.uniwa.bookstore.model.RentalStatus;
import com.uniwa.bookstore.repository.BookRepository;
import com.uniwa.bookstore.repository.RentalRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    // private final RentalRepository rentalRepository;

    // @Autowired
    // public RentalService(RentalRepository rentalRepository) {
    // this.rentalRepository = rentalRepository;
    // }

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Optional<Rental> rentBook(Long userId, Long bookId) {
        // 1. Check if the book is available
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            return Optional.empty(); // Book not found
        }

        Book book = bookOptional.get();
        if (book.getAvailableCopies() <= 0) {
            return Optional.empty(); // No copies available
        }

        // 2. Rent a Book (if available_copies > 0)
        // Decrease available copies of the book
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // 3. Create the rental record
        Rental rental = new Rental();
        rental.setUser(userId);
        rental.setBook(bookId);
        rental.setRentDate(LocalDateTime.now());
        rental.setDueDate(LocalDate.now().plusDays(14));
        rental.setStatus(RentalStatus.Active); // Set status as Active

        Rental savedRental = rentalRepository.save(rental);

        return Optional.of(savedRental);
    }

    // Get all rentals
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Get rental by ID
    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    // Create a new rental
    // public Rental createRental(Rental rental) {
    // return rentalRepository.save(rental);
    // }
    public Rental createRental(Rental rental) {
        // Define the formatter for the desired date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Set the current date and time as rent_date with the proper format
        LocalDateTime rentDate = LocalDateTime.now();
        rental.setRentDate(LocalDateTime.parse(rentDate.format(formatter), formatter));

        // Calculate due_date as 14 days from the rent_date
        LocalDate dueDate = LocalDate.now().plus(14, ChronoUnit.DAYS);
        rental.setDueDate(dueDate);

        // Save the rental to the database
        return rentalRepository.save(rental);
    }

    // Update an existing rental
    public Optional<Rental> updateRental(Long id, Rental rentalDetails) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            rental.setUser(rentalDetails.getUser());
            rental.setBook(rentalDetails.getBook());
            rental.setRentDate(rentalDetails.getRentDate());
            rental.setDueDate(rentalDetails.getDueDate());
            rental.setStatus(rentalDetails.getStatus());
            rental.setReturnDate(rentalDetails.getReturnDate());
            return Optional.of(rentalRepository.save(rental));
        }
        return Optional.empty();
    }

    // Delete a rental
    public boolean deleteRental(Long id) {
        if (rentalRepository.existsById(id)) {
            rentalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Update rental status
    public Optional<Rental> updateRentalStatus(Long id, RentalStatus status) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            rental.setStatus(status);
            return Optional.of(rentalRepository.save(rental));
        }
        return Optional.empty();
    }

    // Return a book
    public Optional<Rental> returnBook(Long rentalId) {
        Logger logger = LoggerFactory.getLogger(RentalController.class);

        logger.info("Attempting to return book for rentalId: {}", rentalId);

        Optional<Rental> rentalOptional = rentalRepository.findById(rentalId);

        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();

            if ("Active".equals(rental.getStatus().name())) {
                // Update rental status and return date
                rental.setStatus(RentalStatus.Returned);
                rental.setReturnDate(LocalDateTime.now());
                rentalRepository.save(rental);

                // Update the book's available copies
                Optional<Book> bookOptional = bookRepository.findById(rental.getBook());
                if (bookOptional.isPresent()) {
                    Book book = bookOptional.get();
                    book.setAvailableCopies(book.getAvailableCopies() + 1);
                    bookRepository.save(book);
                    logger.info("Book returned successfully. Updated available copies for bookId: {}. New count: {}",
                            rental.getBook(), book.getAvailableCopies());

                    return Optional.of(rental);
                } else {
                    logger.error("Book with id: {} not found during return.", rental.getBook());
                }
            } else {
                logger.error("Rental with rentalId: {} is already returned or invalid.", rentalId);
            }
        } else {
            logger.error("Rental record not found for rentalId: {}", rentalId);
        }

        return Optional.empty(); // If rental is not found or not active
    }

    // Get all rentals by userId
    public List<Rental> getRentalsByUser(Long userId) {
        return rentalRepository.findByUserId(userId);
    }

    // Get active rentals by userId
    public List<Rental> getActiveRentalsByUser(Long userId) {
        return rentalRepository.findByUserIdAndStatus(userId, RentalStatus.Active);
    }
}
