package com.LibSys.OctSky.backend.Service;

import com.LibSys.OctSky.backend.model.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VisitorService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List findVisitor(){
        String sql = "SELECT * FROM visitorview";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Visitor(rs.getInt("visitorNumber"), rs.getInt("cardNumber"), rs.getString("ssn"), rs.getString("firstname"), rs.getString("surname")));
        }
        catch (Exception e){
            return new ArrayList();
        }
    }

    public void deleteVisitor(int visitorNumber)
    {
        String sql = "CALL deleteVisitor (?)";
        jdbcTemplate.update(sql, visitorNumber);
    }

    public void addVisitor(String ssn, String firstname, String surname, String encrypt_pass)
    {
        String sql = "CALL addVisitor(?,?,?,?)";
        jdbcTemplate.update(sql, ssn, firstname, surname, encrypt_pass);
    }
}
