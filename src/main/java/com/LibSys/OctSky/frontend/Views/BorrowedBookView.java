package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.BookService;
import com.LibSys.OctSky.backend.model.Book;
import com.LibSys.OctSky.backend.model.BorrowedBook;
import com.LibSys.OctSky.backend.model.VisitorBook;
import com.LibSys.OctSky.frontend.layouts.AdminLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "borrow", layout = AdminLayout.class)
@PageTitle("Lånade böcker")

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
        grid.setColumns("title", "borrowdate", "returndate", "cardnumber", "firstname", "surname", "email","booknumber");
        grid.getColumnByKey("title").setHeader("Titel");
        grid.getColumnByKey("borrowdate").setHeader("Lånedatum");
        grid.getColumnByKey("returndate").setHeader("Returdatum");
        grid.getColumnByKey("cardnumber").setHeader("Kortnummer");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("surname").setHeader("Efternamn");
        grid.getColumnByKey("email").setHeader("E-post");
        grid.getColumnByKey("booknumber").setHeader("Exemplarnummer");
        grid.addComponentColumn(item -> createReturnButton(item)).setKey("return");
    }
    public void populateGrid()
    {
        grid.setItems(bookService.findBorrowedBooks());
    }

    public Button createReturnButton(BorrowedBook item)
    {
        Button button = new Button("Återlämnad", clickEvent -> {
        bookService.returnBook(item.getId(), item.getCardnumber());
        populateGrid();
        });

        return button;
    }

}
