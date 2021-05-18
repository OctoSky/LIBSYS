package com.LibSys.OctSky.backend.model;

public class Book {
     int id;
    String price;
    String title;
    String writer;
    String isbn;
    String description;
    String dewey;
    String category;
    String publisher;
    int categoryId;
    int publisherId;
    String ebook;
    int amount;
     public Book(int id, String price, String title, String writer,
                 String isbn, String description, String dewey, String category,
                 String publisher, int categoryId, int publisherId, String ebook, int amount)
     {

         this.id = id;
         this.price = price;
         this.title = title;
         this.writer = writer;
         this.isbn = isbn;
         this.description = description;
         this.dewey = dewey;
         this.category = category;
         this.publisher = publisher;
         this.categoryId = categoryId;
         this.publisherId = publisherId;
         this.ebook = ebook;
         this.amount = amount;
     }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEbook() {
        return ebook;
    }

    public void setEbook(String ebook) {
        this.ebook = ebook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDewey() {
        return dewey;
    }

    public void setDewey(String dewey) {
        this.dewey = dewey;
    }
}
