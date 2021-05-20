package com.LibSys.OctSky.backend.model;

public class BorrowedBook {
    String title;
    String borrowdate;
    String returndate;
    int cardnumber;
    String firstname;
    String surname;
    String email;

    public BorrowedBook(String title, String borrowdate, String returndate, int cardnumber, String firstname, String surname, String email)
    {
        this.title = title;
        this.borrowdate = borrowdate;
        this.returndate = returndate;
        this.cardnumber = cardnumber;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(String borrowdate) {
        this.borrowdate = borrowdate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public int getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(int cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
