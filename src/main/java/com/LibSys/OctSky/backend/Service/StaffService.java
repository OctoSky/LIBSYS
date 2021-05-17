package com.LibSys.OctSky.backend.Service;


import com.LibSys.OctSky.backend.model.Credentials;
import com.LibSys.OctSky.backend.model.Roles;
import com.LibSys.OctSky.backend.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StaffService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List findStaff(){

        try
        {
            return jdbcTemplate.query("SELECT id, firstname, surname, phone, email, role FROM staffview", (rs, rowNum) -> new Staff(rs.getInt("id"), rs.getString("role"), rs.getString("firstname"), rs.getString("surname"), rs.getString("phone"), rs.getString("email")));
        }
        catch(Exception e)
        {
            return new ArrayList();
        }

    }

    public List findRoles () {
        try {
            return jdbcTemplate.query("SELECT * FROM Roles", (rs, rowNum) -> new Roles(rs.getInt("id"), rs.getString("roleName")));
        } catch(Exception e)
        {
            return new ArrayList();
        }
    }

    public void savestaff(int staffid,
                          String firstname,
                          String surname,
                          String phone,
                          String email,
                          int role_id,
                          String password,
                          String enc_pass)
    {
        String sql = "CALL updatestaff(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, staffid, firstname, surname, phone, email, role_id, password, enc_pass);
    }

    public List findCredentials() {
        try {
            return jdbcTemplate.query("SELECT * FROM Credentials", (rs, rowNum) -> new Credentials(rs.getString("Staff_email"), rs.getString("password")));
        } catch(Exception e) {
            return new ArrayList();
        }
    }

    public void addStaff(String firstName, String surName, String phone, String email, int role, String password, String encrypt_pass){
        String sql = "CALL addstaff(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,firstName,surName,phone,email,role,password, encrypt_pass);

    }

    public void deleteStaff(int id){

        String sql = "CALL removeStaff(?)";
        jdbcTemplate.update(sql,id);

    }
}
