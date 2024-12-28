package com.uniwa.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {

    // Custom query to get books by a user's ID
    @Query("SELECT b FROM FavoriteList f JOIN f.book b WHERE f.user.id = :userId")
    List<Book> findBooksByUserId(Long userId);
}
