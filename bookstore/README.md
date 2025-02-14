# Bookstore API Documentation

## Overview

This API allows users to manage a bookstore, including functionalities for user authentication, book management, rentals, and favorites.

## Endpoints

### User Authentication

#### Login

- **URL:** `/api/login`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "username": "testuser01",
    "password": "password123"
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "username": "testuser01",
    "message": "Login successful"
  }
  ```

#### Register

- **URL:** `/api/register`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "username": "newuser",
    "password": "newpassword"
  }
  ```
- **Response:** `User registered successfully`

### Book Management

#### Get All Books

- **URL:** `/api/books`
- **Method:** `GET`
- **Response:**
  ```json
  [
    {
      "id": 1,
      "title": "Book Title",
      "author": "Author Name",
      "availableCopies": 5
    }
  ]
  ```

#### Get Book by ID

- **URL:** `/api/books/{bookid}`
- **Method:** `GET`
- **Response:**
  ```json
  {
    "id": 1,
    "title": "Book Title",
    "author": "Author Name",
    "availableCopies": 5
  }
  ```

#### Add a New Book

- **URL:** `/api/books`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "title": "New Book",
    "author": "New Author",
    "availableCopies": 10
  }
  ```
- **Response:**
  ```json
  {
    "id": 2,
    "title": "New Book",
    "author": "New Author",
    "availableCopies": 10
  }
  ```

#### Update an Existing Book

- **URL:** `/api/books/{bookid}`
- **Method:** `PUT`
- **Request Body:**
  ```json
  {
    "title": "Updated Book",
    "author": "Updated Author",
    "availableCopies": 8
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "title": "Updated Book",
    "author": "Updated Author",
    "availableCopies": 8
  }
  ```

### Rental Management

#### Rent a Book

- **URL:** `/api/rentals/rent`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "user": 1,
    "book": 1
  }
  ```
- **Response:** `Book rented successfully.`

#### Return a Book

- **URL:** `/api/rentals/return/{rentalId}`
- **Method:** `POST`
- **Response:** `Book returned successfully.`

### Favorite Management

#### Add a Favorite Book

- **URL:** `/api/v1/favorites/add`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "userId": 1,
    "bookId": 1
  }
  ```
- **Response:** `Favorite added successfully`

#### Remove a Favorite Book

- **URL:** `/api/v1/favorites/remove/{favoriteId}`
- **Method:** `DELETE`
- **Response:** `Favorite book removed successfully`

#### Get Favorite Books

- **URL:** `/api/v1/favorites/view/{userId}`
- **Method:** `GET`
- **Response:**
  ```json
  [
    {
      "id": 1,
      "title": "Book Title",
      "author": "Author Name"
    }
  ]
  ```

## Database

### User Table

```sql
mysql> describe user;
+------------+--------------+------+-----+-------------------+-------------------+
| Field      | Type         | Null | Key | Default           | Extra             |
+------------+--------------+------+-----+-------------------+-------------------+
| id         | bigint       | NO   | PRI | NULL              | auto_increment    |
| username   | varchar(255) | YES  | UNI | NULL              |                   |
| password   | varchar(255) | NO   |     | NULL              |                   |
| created_at | timestamp    | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+------------+--------------+------+-----+-------------------+-------------------+
```

### Sample Data

```sql
mysql> select * from user;
+----+------------+--------------------------------------------------------------+---------------------+
| id | username   | password                                                     | created_at          |
+----+------------+--------------------------------------------------------------+---------------------+
|  1 | testuser   | $2a$10$eBqjSMzZcODyGl5IjXvAeOgWaVfZ8O8ThAS98RAURUwSPH8UmHyPa | 2024-12-26 13:18:46 |
|  2 | user       | $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36AX3udmyo7VoM0Lk4eNx2W | 2024-12-26 13:22:31 |
|  3 | tester     | $2a$10$LuaP59riygmRcyANbUQbd.g52hrAafRWkJR.Yf128u4uG8.q3/YWO | 2024-12-26 13:39:00 |
|  5 | john_doe   | securepassword                                               | 2024-12-26 13:50:24 |
|  7 | admin      | admin123                                                     | 2024-12-27 12:10:57 |
|  8 | testuser01 | password123                                                  | 2024-12-27 12:11:26 |
+----+------------+--------------------------------------------------------------+---------------------+
```
