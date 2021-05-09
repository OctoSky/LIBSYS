package com.LibSys.OctSky.backend.model;

public class Roles {

    private int roleId;
    private String roleName;
    public Roles(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        switch(this.roleId)
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

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
