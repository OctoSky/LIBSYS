package com.LibSys.OctSky.backend.model;

public class Person {
    String firstname;
    String surname;
    public Person(String firstname, String surname)
    {
        this.firstname = firstname;
        this.surname = surname;
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
}
