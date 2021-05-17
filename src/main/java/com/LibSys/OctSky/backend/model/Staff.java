package com.LibSys.OctSky.backend.model;

public class Staff {
    

    private int id;
    private int roles;
    private String firstname;
    private String surname;
    private String phone;
    private String email;

    public Staff(int id, int roles, String firstname, String surname, String phone, String email) {

        this.id = id;
        this.roles = roles;
        this.firstname = firstname;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getroles() {
        switch(roles)
        {
            case(1):
                return "Administratör";
            case(2):
                return "Bibliotekarie";
            case(3):
                return "Kund";
            default:
                return "";
        }
    }

    public int getRoleId()
    {
        return roles;
    }

    public void setroles(int roles) {
        this.roles = roles;
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getsurname() {

        return surname;
    }

    public void setsurname(String surname) {

        this.surname = surname;
    }

    public String getphone() {

        return phone;
    }

    public void setphone(String phone) {

        this.phone = phone;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {

        this.email = email;
    }



    }

