package com.LibSys.OctSky.backend.model;

import javax.validation.OverridesAttribute;

public class BookNumber {
    public enum Status{available, borrowed, archived}
    private int id;
    private int books_id;
    private Status status;
    private String comment;

    public BookNumber(int id, int books_id, String status , String comment) {
        this.id = id;
        this.books_id = books_id;
        this.status = Status.valueOf(status);
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBooks_id() {
        return books_id;
    }

    public void setBooks_id(int books_id) {
        this.books_id = books_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIdAsString (){
     String string = String.valueOf(this.id);
        return string;
    }


    @Override
    public String toString() {
        return "BookNumber{" +
                "id=" + id +
                '}';
    }
}
