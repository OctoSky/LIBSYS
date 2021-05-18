package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.BookService;
import com.LibSys.OctSky.backend.model.Book;
import com.LibSys.OctSky.backend.model.VisitorBook;
import com.LibSys.OctSky.frontend.layouts.VisitorLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "home", layout = VisitorLayout.class)
public class HomePageView extends VerticalLayout {

BookService bookService;
Grid<VisitorBook> grid = new Grid<>(VisitorBook.class);

    TextField writerFilter = new TextField();
    TextField titleFilter = new TextField();
    TextField deweyFilter = new TextField();
    TextField amountFilter = new TextField();

    HorizontalLayout filterLayout = new HorizontalLayout(titleFilter, writerFilter, deweyFilter, amountFilter);

    public HomePageView(BookService bookService) {
        this.bookService = bookService;
        this.setSizeFull();
        populateGrid();
        configureGrid();
        this.add(grid);
    }

    private void configureGrid() {
        grid.setColumns("title", "writer", "description", "category", "publisher", "dewey", "ebook", "amount");
        grid.getColumnByKey("title").setHeader("Titel");
        grid.getColumnByKey("writer").setHeader("Författare");
        grid.getColumnByKey("category").setHeader("Kategori");
        grid.getColumnByKey("publisher").setHeader("Utgivare");
        grid.getColumnByKey("dewey").setHeader("Placering");
        grid.getColumnByKey("ebook").setHeader("Finns som E-bok");
        grid.getColumnByKey("amount").setHeader("Tillgänglig");
        grid.removeColumnByKey("description");
        grid.addComponentColumn(item -> createDescriptionButton(item))
                .setKey("beskrivning");
        grid.getColumnByKey("beskrivning").setHeader("Beskrivning");
    }

    public Button createDescriptionButton(VisitorBook item)
    {
        Button button = new Button("Visa", clickEvent -> {
            Div taskNotification = new Div();
            Notification notification = new Notification(taskNotification);
            taskNotification.addClickListener(listener ->
                    notification.close());
            Label label = new Label(item.getDescription());
            notification.setPosition(Notification.Position.MIDDLE);
            taskNotification.add(label);
            notification.add(taskNotification);
            notification.setVisible(true);
            notification.open();
        });

        return button;
    }
    private void populateGrid() {
        grid.setItems(bookService.findVisitorBooks());
    }

}
