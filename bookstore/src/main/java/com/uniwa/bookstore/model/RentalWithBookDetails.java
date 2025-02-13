package com.uniwa.bookstore.model;

public class RentalWithBookDetails {
    private Long id;
    private String rentDate;
    private String dueDate;
    private String returnDate;
    private String status;
    private String bookTitle;
    private String bookAuthor;
    private Long userId;

    // Constructor
    public RentalWithBookDetails(Rental rental, Book book) {
        this.id = rental.getId();
        this.rentDate = rental.getRentDate().toString(); // Convert LocalDateTime to String
        this.dueDate = rental.getDueDate().toString(); // Convert LocalDate to String
        this.returnDate = rental.getReturnDate() != null ? rental.getReturnDate().toString() : null;
        this.status = rental.getStatus().toString(); // Enum to String
        this.bookTitle = book.getTitle();
        this.bookAuthor = book.getAuthor();
        this.userId = rental.getUser();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // toString method
    @Override
    public String toString() {
        return "RentalWithBookDetails{" +
                "id=" + id +
                ", rentDate='" + rentDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", status='" + status + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", userId=" + userId +
                '}';
    }
}