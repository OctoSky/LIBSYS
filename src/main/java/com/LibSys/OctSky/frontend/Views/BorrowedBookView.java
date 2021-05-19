package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.BookService;
import com.LibSys.OctSky.backend.model.Book;
import com.LibSys.OctSky.backend.model.BorrowedBook;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "låna")
public class BorrowedBookView extends VerticalLayout {
    public BookService bookService;
    public Grid<BorrowedBook> grid = new Grid<>(BorrowedBook.class);

    public BorrowedBookView(BookService bookService)
    {
        this.bookService = bookService;
        this.setSizeFull();
        configureGrid();
        populateGrid();

        add(grid);
    }
    public void configureGrid()
    {
        grid.setColumns("title", "borrowdate", "returndate", "cardnumber", "firstname", "surname", "email");
        grid.getColumnByKey("title").setHeader("Titel");
        grid.getColumnByKey("borrowdate").setHeader("Lånedatum");
        grid.getColumnByKey("returndate").setHeader("Returdatum");
        grid.getColumnByKey("cardnumber").setHeader("Kortnummer");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("surname").setHeader("Efternamn");
        grid.getColumnByKey("email").setHeader("E-post");
    }
    public void populateGrid()
    {
        grid.setItems(bookService.findBorrowedBooks());
    }



}
