package com.LibSys.OctSky.backend.model;

public class AddBookObject {
    private Book book;
    private Publisher publisher;
    private Category category;
    private Ebook ebook;
    public AddBookObject(Book book, Publisher publisher, Category category, Ebook ebook)
    {

        this.book = book;
        this.publisher = publisher;
        this.category = category;
        this.ebook = ebook;
    }

    public Ebook getEbook() {
        return ebook;
    }

    public void setEbook(Ebook ebook) {
        this.ebook = ebook;
    }

    public Book getBook() {
        return book;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Category getCategory() {
        return category;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
