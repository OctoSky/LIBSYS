package com.LibSys.OctSky.backend.model;

public class LibraryCardObject {



    private int cardnumber;
    private int visitor_socialsecuritynumber;


    public LibraryCardObject(int cardnumber, int visitor_socialsecuritynumber) {
        this.cardnumber = cardnumber;
        this.visitor_socialsecuritynumber = visitor_socialsecuritynumber;
    }

    public int getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(int cardnumber) {
        this.cardnumber = cardnumber;
    }

    public int getVisitor_socialsecuritynumber() {
        return visitor_socialsecuritynumber;
    }

    public void setVisitor_socialsecuritynumber(int visitor_socialsecuritynumber) {
        this.visitor_socialsecuritynumber = visitor_socialsecuritynumber;
    }

}
