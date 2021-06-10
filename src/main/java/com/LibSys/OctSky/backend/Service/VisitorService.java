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
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Visitor(rs.getInt("roles_id"),
                            rs.getInt("visitorNumber"),
                    rs.getInt("cardNumber"),
                    rs.getString("ssn"),
                    rs.getString("firstname"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                    )
            );

        }
        catch (Exception e){
            return new ArrayList();
        }
    }

    public void disableCard(int cardnumber, int roleid)
    {
        String sql = "CALL disablecard(?,?)";
        jdbcTemplate.update(sql, cardnumber, roleid);
    }

    public void deleteVisitor(int visitorNumber)
    {
        String sql = "CALL deleteVisitor (?)";
        jdbcTemplate.update(sql, visitorNumber);
    }

    public void savevisitor(int visitorid,
                            String SSN,
                            String vfirstname,
                            String vsurname,
                            String vemail,
                            String vphone,
                            String vaddress,
                            String enc_pass)
    {
        String sql = "CALL updatevisitor(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, visitorid, SSN, vfirstname, vsurname, vemail, vphone, vaddress, enc_pass);
    }

    public void addVisitor(int cardNumber,String ssn,
                           String firstname,
                           String surname,
                           String email,
                           String phone,
                           String address,
                           String encrypt_pass,
                           String pass_in)
    {
        String sql = "CALL addVisitor(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,cardNumber, ssn, firstname, surname, email, phone, address, pass_in, encrypt_pass);
    }
}
