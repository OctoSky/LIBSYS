package com.LibSys.OctSky.backend.model;

public class Visitor {

    private int cardnumber;
    private String socialsecuritynumber;
    private String firstname;
    private String surname;


    public Visitor(int cardnumber,String socialsecuritynumber, String firstname, String surname) {
        this.cardnumber = cardnumber;
        this.socialsecuritynumber = socialsecuritynumber;
        this.firstname = firstname;
        this.surname = surname;
    }
    public String getSocialsecuritynumber() {
        return socialsecuritynumber;
    }

    public void setSocialsecuritynumber(String socialsecuritynumber) {
        this.socialsecuritynumber = socialsecuritynumber;
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
