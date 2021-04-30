package com.LibSys.OctSky.backend.Service;


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
            return jdbcTemplate.query("SELECT id, firstname, surname, phone, email, role FROM staffview", (rs, rowNum) -> new Staff(rs.getInt("id"), rs.getString("roles"), rs.getString("firstname"), rs.getString("surname"), rs.getString("phone"), rs.getString("email")));
        }
        catch(Exception e)
        {
            return new ArrayList();
        }

    }
}
