package com.uniwa.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniwa.bookstore.model.Rental;
import com.uniwa.bookstore.model.RentalStatus;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findById(Long rentalId);
    // JpaRepository provides built-in methods like save, findAll, etc.

    List<Rental> findByUserIdAndStatus(Long userId, RentalStatus status);

    List<Rental> findByStatus(RentalStatus status);

    List<Rental> findByUserId(Long userId);
}
