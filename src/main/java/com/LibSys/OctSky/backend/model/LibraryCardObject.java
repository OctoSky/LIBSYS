package com.LibSys.OctSky.backend.model;

public class LibraryCardObject {



    private int cardnumber;
    private int visitorNumber;


    public LibraryCardObject(int cardnumber, int visitor_socialsecuritynumber) {
        this.cardnumber = cardnumber;
        this.visitorNumber = visitor_socialsecuritynumber;
    }

    public int getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(int cardnumber) {
        this.cardnumber = cardnumber;
    }

    public int getVisitorNumber() {
        return visitorNumber;
    }

    public void setVisitorNumber(int visitorNumber) {
        this.visitorNumber = visitorNumber;
    }

}
