package com.LibSys.OctSky.backend.model;


public class AddUserObject {
private Staff staff;
private Roles role;


    public AddUserObject(Staff staff, Roles role) {
        this.staff = staff;
        this.role = role;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
