-- Step 1: Create Database
DROP DATABASE IF EXISTS bookshop;
CREATE DATABASE IF NOT EXISTS bookshop;
USE bookshop;

-- Step 2: Create Tables

-- Table: user
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: book
CREATE TABLE IF NOT EXISTS book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Changed from INT to BIGINT
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    genre VARCHAR(50),
    isbn VARCHAR(13) UNIQUE NOT NULL,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL CHECK (available_copies >= 0)
);

-- Table: rental
CREATE TABLE IF NOT EXISTS rental (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,  -- Matches BIGINT type in book.id
    rent_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date DATE NOT NULL,
    return_date TIMESTAMP,
    status ENUM('Active', 'Returned', 'Overdue') DEFAULT 'Active',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE  -- References book.id
);

-- Step 3: Populate Data

-- Add Users
INSERT INTO user (username, password) 
VALUES
('alice_smith', 'password123'),
('bob_johnson', 'securepass'),
('charlie_brown', 'mypassword'),
('diana_prince', 'wonderwoman'),
('evan_parker', 'parksecure');

-- Add Books
INSERT INTO book (title, author, genre, isbn, total_copies, available_copies) 
VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', '9780743273565', 10, 9),  -- 1 rented
('To Kill a Mockingbird', 'Harper Lee', 'Fiction', '9780061120084', 8, 7),       -- 1 rented
('1984', 'George Orwell', 'Dystopian', '9780451524935', 5, 3),                   -- 2 rented
('Pride and Prejudice', 'Jane Austen', 'Romance', '9781503290563', 10, 9),       -- 1 rented
('The Catcher in the Rye', 'J.D. Salinger', 'Fiction', '9780316769488', 7, 6),   -- 1 rented
('Moby Dick', 'Herman Melville', 'Adventure', '9781503280786', 6, 5);            -- 1 rented

-- Add Rentals
INSERT INTO rental (user_id, book_id, due_date) 
VALUES
(1, 1, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY)),
(1, 3, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY)),
(2, 2, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY)),
(3, 4, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY)),
(3, 5, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY)),
(4, 6, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY)),
(5, 3, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY));

-- Step 4: Logic for Renting System

-- 1. Check if the book is available
SELECT available_copies FROM book WHERE id = 1;

-- 2. Rent a Book (if available_copies > 0)
BEGIN;
    UPDATE book SET available_copies = available_copies - 1 WHERE id = 1 AND available_copies > 0;
    INSERT INTO rental (user_id, book_id, due_date) 
    VALUES (1, 1, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY));
COMMIT;

-- 3. Return a Book
BEGIN;
    UPDATE rental 
    SET return_date = CURRENT_TIMESTAMP, 
        status = 'Returned'
    WHERE id = 1;
    UPDATE book SET available_copies = available_copies + 1 WHERE id = 1;
COMMIT;

-- 4. List Overdue Rentals
SELECT rental.*, user.username AS username, book.title AS booktitle
FROM rental
JOIN user ON rental.user_id = user.id
JOIN book ON rental.book_id = book.id
WHERE rental.due_date < CURRENT_DATE AND rental.status = 'Active';



USE bookshop;

-- Drop the table if it already exists
DROP TABLE IF EXISTS favorite_list;

-- Create the favorite_list table
CREATE TABLE favorite_book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);

-- Insert example data
INSERT INTO favorite_book (user_id, book_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(5, 1),
(5, 2);

-- Query to verify the table content
SELECT * FROM favorite_book;
