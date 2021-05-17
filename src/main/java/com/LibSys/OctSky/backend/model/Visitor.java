package com.LibSys.OctSky.backend.model;

public class Visitor {

    private int visitorNumber;
    private int cardnumber;
    private String socialsecuritynumber;
    private String firstname;
    private String surname;
    private String email, phone, address;


    public Visitor(int visitorNumber, int cardnumber, String socialsecuritynumber, String firstname, String surname, String email, String phone, String address) {
        this.visitorNumber = visitorNumber;
        this.cardnumber = cardnumber;
        this.socialsecuritynumber = socialsecuritynumber;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSocialsecuritynumber() {
        return socialsecuritynumber;
    }

    public void setSocialsecuritynumber(String socialsecuritynumber) {
        this.socialsecuritynumber = socialsecuritynumber;
    }

    public int getVisitorNumber() {
        return visitorNumber;
    }

    public void setVisitorNumber(int visitorNumber) {
        this.visitorNumber = visitorNumber;
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

    public int getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(int cardnumber) {
        this.cardnumber = cardnumber;
    }
}
