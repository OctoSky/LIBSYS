package com.LibSys.OctSky.backend.Service;

import com.LibSys.OctSky.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List findBooks()
    {
        String sql = "SELECT * FROM staffbookview";
        try
        {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Book(rs.getInt("id"),
                    rs.getString("price"),
                    rs.getString("title"),
                    rs.getString("writer"),
                    rs.getString("isbn"),
                    rs.getString("description"),
                    rs.getString("dewey"),
                    rs.getString("category"),
                    rs.getString("publisher"),
                    rs.getInt("categoryid"),
                    rs.getInt("publisherid"),
                    rs.getString("ebook"),
                    rs.getInt("amount")));
        }
        catch(Exception e)
        {
            return new ArrayList();
        }
    }

    public List findVisitorBooks() {

        String sql = "SELECT * FROM visitorbookview";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new VisitorBook(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("writer"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("publisher"),
                    rs.getString("dewey"),
                    rs.getString("ebook"),
                    rs.getInt("amount")));
        }
        catch(Exception e)
        {
            return new ArrayList();
        }
    }

    public void borrowBook(int bookId, int cardNumber, String dateToday, String returnDate) {
        String sql = "CALL borrowbook(?,?,?,?)";
        jdbcTemplate.update(sql, bookId, cardNumber, dateToday, returnDate);
    }

    public List findBorrowedBooks()
    {
        String sql = "SELECT * FROM borrowedbooksview";

        try
        {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new BorrowedBook(rs.getString("title"),
                    rs.getString("borrowdate"),
                    rs.getString("returndate"),
                    rs.getInt("cardNumber"),
                    rs.getString("firstname"),
                    rs.getString("surname"),
                    rs.getString("email")));
        }
        catch (Exception e)
        {
            return new ArrayList();
        }
    }

    public List findArchivedBooks()
    {
        String sql = "SELECT * FROM archivedbooksview";
        try
        {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new ArchivedBooks(rs.getInt("id"),
                    rs.getString("price"),
                    rs.getString("title"),
                    rs.getString("writer"),
                    rs.getString("isbn"),
                    rs.getString("description"),
                    rs.getString("dewey"),
                    rs.getString("category"),
                    rs.getString("publisher"),
                    rs.getInt("categoryid"),
                    rs.getInt("publisherid"),
                    rs.getString("reason")));
        }
        catch(Exception e)
        {
            return new ArrayList();
        }
    }

    public List findCategories()
    {
        String sql = "SELECT * FROM category";
        try
        {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Category(rs.getInt("id"), rs.getString("name")));
        }
        catch(Exception e)
        {
            return new ArrayList();
        }

    }

    public void savebook(int bookId, String title,
                         String writer,
                         String isbn,
                         String description,
                         String price,
                         String dewey,
                         int publisherid,
                         int categoryid,
                         String ebook, int amount)
    {
        String sql = "CALL updatebook(?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, bookId, title, writer, isbn, description, price, dewey, publisherid, categoryid, ebook, amount);
    }

    public void addnewbook(String title,
                           String writer,
                           String isbn,
                           String description,
                           String price,
                           String dewey,
                           int publisherid,
                           int categoryid,
                           String ebook, int amount)
    {
        String sql = "CALL addnewbook(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, title, writer, isbn, description, price, dewey, publisherid, categoryid, ebook, amount);
    }
    public void deleteBook(int id, String reason)
    {
        String sql = "CALL deletebook(?,?)";
        jdbcTemplate.update(sql,id, reason);
    }

    public List findPublishers()
    {
        String sql = "SELECT * FROM publisher";
        try
        {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Publisher(rs.getInt("id"), rs.getString("name")));
        }
        catch(Exception e)
        {
            return new ArrayList();
        }
    }

}
