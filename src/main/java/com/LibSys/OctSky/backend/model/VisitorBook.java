package com.LibSys.OctSky.backend.model;

public class VisitorBook {

    int id;
    String title;
    String writer;
    String description;
    String category;
    String publisher;
    String dewey;
    String ebook;
    int amount;


    public VisitorBook(int id, String title, String writer, String description, String category, String publisher, String dewey, String ebook, int amount) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
        this.dewey = dewey;
        this.ebook = ebook;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDewey() {
        return dewey;
    }

    public void setDewey(String dewey) {
        this.dewey = dewey;
    }

    public String getEbook() {
        return ebook;
    }

    public void setEbook(String ebook) {
        this.ebook = ebook;
    }

    public String getAmount() {
        String returnValue = "";
        if (this.amount > 0) {
            returnValue =  "Ja";
        }
        else {
            returnValue = "Nej";
        }
        return returnValue;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
