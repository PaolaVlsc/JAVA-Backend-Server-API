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
    available_copies INT NOT NULL CHECK (available_copies >= 0),
    short_description TEXT  -- Changed from VARCHAR(255) to TEXT
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
-- INSERT INTO book (title, author, genre, isbn, total_copies, available_copies, short_description) 
-- VALUES
-- ('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', '9780743273565', 10, 9, 'A classic novel.'),
-- ('To Kill a Mockingbird', 'Harper Lee', 'Fiction', '9780061120084', 8, 7, 'A novel about justice.'),
-- ('1984', 'George Orwell', 'Dystopian', '9780451524935', 5, 3, 'A dystopian classic.'),
-- ('Pride and Prejudice', 'Jane Austen', 'Romance', '9781503290563', 10, 9, 'A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.A romantic novel.'),
-- ('The Catcher in the Rye', 'J.D. Salinger', 'Fiction', '9780316769488', 7, 6, 'A novel about youth.'),
-- ('Moby Dick', 'Herman Melville', 'Adventure', '9781503280786', 6, 5, 'An adventure novel.');


INSERT INTO book
    (title, author, genre, isbn, total_copies, available_copies, short_description)
VALUES
('To Kill a Mockingbird', 'Harper Lee', 'Fiction, Classic', '9780000000001', 10, 10, 'A classic novel depicting racial injustice in the American South.'),
('1984', 'George Orwell', 'Dystopian, Science Fiction', '9780000000002', 10, 10, 'A dystopian novel portraying a totalitarian society.'),
('Pride and Prejudice', 'Jane Austen', 'Classic, Romance', '9780000000003', 10, 10, 'A classic novel exploring themes of love, marriage, and social norms.'),
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction, Classic', '9780000000004', 10, 10, 'A tale of the American Dream, wealth, and love during the Roaring Twenties.'),
('Moby-Dick', 'Herman Melville', 'Fiction, Adventure', '9780000000005', 10, 10, 'The epic tale of Captain Ahab''s obsession with the white whale.'),
('The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy, Adventure', '9780000000006', 10, 10, 'An epic fantasy saga about the quest to destroy the One Ring.'),
('The Catcher in the Rye', 'J.D. Salinger', 'Fiction, Coming-of-age', '9780000000007', 10, 10, 'A classic coming-of-age novel following Holden Caulfield''s journey.'),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy, Adventure', '9780000000008', 10, 10, 'The prequel to The Lord of the Rings, following Bilbo Baggins'' journey.'),
('One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 'Magical Realism, Literary Fiction', '9780000000009', 10, 10, 'A multi-generational saga of the Buendía family in the fictional town of Macondo.'),
('War and Peace', 'Leo Tolstoy', 'Historical Fiction, Epic', '9780000000010', 10, 10, 'A monumental work depicting the events of Russian society during the Napoleonic era.'),
('The Odyssey', 'Homer', 'Epic, Mythology', '9780000000011', 10, 10, 'An ancient Greek epic poem recounting Odysseus'' ten-year journey home after the Trojan War.'),
('The Divine Comedy', 'Dante Alighieri', 'Epic, Poetry', '9780000000012', 10, 10, 'An epic poem that follows the journey of the soul through Hell, Purgatory, and Heaven.'),
('The Brothers Karamazov', 'Fyodor Dostoevsky', 'Classic, Philosophical Fiction', '9780000000013', 10, 10, 'A complex novel exploring themes of spirituality, morality, and human nature.'),
('Crime and Punishment', 'Fyodor Dostoevsky', 'Classic, Psychological Fiction', '9780000000014', 10, 10, 'A psychological thriller revolving around guilt, conscience, and redemption.'),
('The Picture of Dorian Gray', 'Oscar Wilde', 'Gothic, Philosophical Fiction', '9780000000015', 10, 10, 'A novel about a man whose portrait ages while he retains his youth and beauty.'),
('Brave New World', 'Aldous Huxley', 'Dystopian, Science Fiction', '9780000000016', 10, 10, 'A dystopian vision of a future society obsessed with pleasure and conformity.'),
('The Count of Monte Cristo', 'Alexandre Dumas', 'Adventure, Historical Fiction', '9780000000017', 10, 10, 'An adventure novel of revenge, love, and redemption set in the early 19th century.'),
('Anna Karenina', 'Leo Tolstoy', 'Classic, Romance', '9780000000018', 10, 10, 'A tragic love story set against the backdrop of Russian high society.'),
('The Alchemist', 'Paulo Coelho', 'Fiction, Philosophical', '9780000000019', 10, 10, 'A philosophical novel about a shepherd boy''s journey to find his personal legend.'),
('The Adventures of Huckleberry Finn', 'Mark Twain', 'Adventure, Satire', '9780000000020', 10, 10, 'A satirical novel following Huck Finn''s journey down the Mississippi River.'),
('The Iliad', 'Homer', 'Epic, Mythology', '9780000000021', 10, 10, 'An ancient Greek epic poem about the Trojan War and the hero Achilles.'),
('The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy, Adventure', '9780000000022', 10, 10, 'A thrilling epic about the quest to destroy the One Ring and save Middle-earth from the dark lord Sauron.'),
('Don Quixote', 'Miguel de Cervantes', 'Classic, Satire', '9780000000023', 10, 10, 'A satirical novel about a deluded knight and his faithful squire, Sancho Panza.'),
('Frankenstein', 'Mary Shelley', 'Gothic, Science Fiction', '9780000000024', 10, 10, 'A novel about the creation of a monster and the consequences of playing god.'),
('Alice''s Adventures in Wonderland', 'Lewis Carroll', 'Fantasy, Children''s Literature', '9780000000025', 10, 10, 'A whimsical tale about a girl named Alice who falls into a magical world.'),
('The Little Prince', 'Antoine de Saint-Exupéry', 'Fable, Children''s Literature', '9780000000026', 10, 10, 'A philosophical novella about a young prince''s journey through the universe.'),
('The Book Thief', 'Markus Zusak', 'Historical Fiction, War', '9780000000027', 10, 10, 'A story of a girl living in Nazi Germany, narrated by Death.'),
('Slaughterhouse-Five', 'Kurt Vonnegut', 'Satire, Science Fiction', '9780000000028', 10, 10, 'An anti-war novel that mixes science fiction and dark humor.'),
('The Grapes of Wrath', 'John Steinbeck', 'Historical Fiction, Social Commentary', '9780000000029', 10, 10, 'A novel about the plight of migrant workers during the Great Depression.'),
('Fahrenheit 451', 'Ray Bradbury', 'Dystopian, Science Fiction', '9780000000030', 10, 10, 'A dystopian novel depicting a future society where books are banned.'),
('The Lord of the Flies', 'William Golding', 'Dystopian, Psychological Fiction', '9780000000031', 10, 10, 'A novel about a group of British boys stranded on an uninhabited island.'),
('The Hitchhiker''s Guide to the Galaxy', 'Douglas Adams', 'Science Fiction, Comedy', '9780000000032', 10, 10, 'A comedic science fiction series about the misadventures of Arthur Dent.'),
('A Tale of Two Cities', 'Charles Dickens', 'Historical Fiction, Classic', '9780000000033', 10, 10, 'A historical novel set during the French Revolution, exploring themes of sacrifice and resurrection.'),
('The Chronicles of Narnia', 'C.S. Lewis', 'Fantasy, Children''s Literature', '9780000000034', 10, 10, 'A series of fantasy novels set in the magical land of Narnia.'),
('The Handmaid''s Tale', 'Margaret Atwood', 'Dystopian, Feminist Fiction', '9780000000035', 10, 10, 'A dystopian novel set in a totalitarian society where women are subjugated.'),
('The Name of the Rose', 'Umberto Eco', 'Historical Fiction, Mystery', '9780000000036', 10, 10, 'A medieval mystery novel set in an Italian monastery.'),
('The Trial', 'Franz Kafka', 'Absurdist Fiction, Existential', '9780000000037', 10, 10, 'A surreal novel exploring themes of guilt, law, and justice.'),
('The Kite Runner', 'Khaled Hosseini', 'Historical Fiction, Drama', '9780000000038', 10, 10, 'A novel about friendship, redemption, and the impact of war in Afghanistan.'),
('The Pillars of the Earth', 'Ken Follett', 'Historical Fiction, Adventure', '9780000000039', 10, 10, 'An epic historical novel set in 12th-century England, centered around the construction of a cathedral.'),
('The Shadow of the Wind', 'Carlos Ruiz Zafón', 'Mystery, Gothic', '9780000000040', 10, 10, 'A mystery novel set in post-war Barcelona, revolving around a forgotten book and its author.'),
('The Secret Garden', 'Frances Hodgson Burnett', 'Children''s Literature, Classic', '9780000000041', 10, 10, 'A classic children''s novel about a young girl who discovers a hidden garden.'),
('The Giver', 'Lois Lowry', 'Dystopian, Young Adult', '9780000000042', 10, 10, 'A dystopian novel about a society with strict control over emotions and memories.'),
('The Metamorphosis', 'Franz Kafka', 'Absurdist Fiction, Existential', '9780000000043', 10, 10, 'A novella about a man who wakes up one morning transformed into a giant insect.'),
('Gone with the Wind', 'Margaret Mitchell', 'Historical Fiction, Romance', '9780000000044', 10, 10, 'A historical novel set during the American Civil War, centered around Scarlett O''Hara.'),
('The Wind in the Willows', 'Kenneth Grahame', 'Children''s Literature, Fantasy', '9780000000045', 10, 10, 'A children''s novel about the adventures of anthropomorphic animals.'),
('Dracula', 'Bram Stoker', 'Gothic, Horror', '9780000000046', 10, 10, 'A Gothic horror novel about the vampire Count Dracula''s attempt to move to England.'),
('The Call of the Wild', 'Jack London', 'Adventure, Nature', '9780000000047', 10, 10, 'An adventure novel about a domestic dog''s life in the wilds of the Yukon.'),
('The Stand', 'Stephen King', 'Horror, Post-Apocalyptic', '9780000000048', 10, 10, 'A post-apocalyptic horror novel about a deadly pandemic and its aftermath.'),
('The Color Purple', 'Alice Walker', 'Fiction, Historical', '9780000000049', 10, 10, 'A novel about the life of African-American women in the Southern United States.'),
('The Silmarillion', 'J.R.R. Tolkien', 'Fantasy, Mythopoeia', '9780000000050', 10, 10, 'A collection of mythopoeic stories about the history of Middle-earth.');



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
