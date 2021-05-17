package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.BookService;
import com.LibSys.OctSky.backend.model.ArchivedBooks;
import com.LibSys.OctSky.backend.model.Book;
import com.LibSys.OctSky.frontend.layouts.AdminLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "arkiv", layout = AdminLayout.class)
@PageTitle("Arkiverade Böcker")
@CssImport("./views/about/about-view.css")
public class ArchivedBooksView extends VerticalLayout {
    private Grid<ArchivedBooks> grid = new Grid<>(ArchivedBooks.class);
    private BookService bookService;
    public ArchivedBooksView(BookService bookService)
    {
        this.bookService = bookService;
        this.setSizeFull();
        configureGrid();
        populateGrid();
        add(grid);
    }


    public void configureGrid()
    {
        grid.setColumns("id","title", "writer", "description", "price", "isbn", "dewey", "category", "publisher","categoryId","publisherId", "reason");
        grid.getColumnByKey("title").setHeader("Titel");
        grid.getColumnByKey("writer").setHeader("Författare");
        grid.getColumnByKey("price").setHeader("Pris");
        grid.getColumnByKey("isbn").setHeader("ISBN");
        grid.getColumnByKey("dewey").setHeader("Dewey");
        grid.getColumnByKey("category").setHeader("Kategori");
        grid.getColumnByKey("publisher").setHeader("Förlag");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("description");
        grid.removeColumnByKey("categoryId");
        grid.removeColumnByKey("publisherId");
        grid.removeColumnByKey("reason");
        grid.addComponentColumn(item -> createReasonButton(item))
                .setKey("anledning");
        grid.getColumnByKey("anledning").setHeader("Anledning");

    }
    public Button createReasonButton(ArchivedBooks item)
    {
        Button button = new Button("Visa", clickEvent -> {
            Div taskNotification = new Div();
            Notification notification = new Notification(taskNotification);
            taskNotification.addClickListener(listener ->
                    notification.close());
            Label label = new Label(item.getReason());
            notification.setPosition(Notification.Position.MIDDLE);
            taskNotification.add(label);
            notification.add(taskNotification);
            notification.setVisible(true);
            notification.open();
        });

        return button;
    }
    public void populateGrid()
    {
        grid.setItems(bookService.findArchivedBooks());
    }
}
