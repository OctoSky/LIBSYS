package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.BookService;
import com.LibSys.OctSky.backend.model.Availability;
import com.LibSys.OctSky.backend.model.Book;
import com.LibSys.OctSky.backend.model.Ebook;
import com.LibSys.OctSky.backend.model.VisitorBook;
import com.LibSys.OctSky.frontend.layouts.VisitorLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Route(value = "", layout = VisitorLayout.class)
public class HomePageView extends VerticalLayout {

BookService bookService;
Grid<VisitorBook> grid = new Grid<>(VisitorBook.class);

    ArrayList<Availability> availabilities = new ArrayList<>();
    private Availability availabilityAll = new Availability("Alla");
    private Availability availabilityJa = new Availability("Tillgänglig");
    private Availability availabilityNo = new Availability("Utlånad");

    private VerticalLayout windowLayout = new VerticalLayout();

    TextField titleFilter = new TextField();
    TextField writerFilter = new TextField();
    TextField deweyFilter = new TextField();
    ComboBox<Availability> amountFilter = new ComboBox<>();
    private VerticalLayout layoutCeption = new VerticalLayout();
    HorizontalLayout filterLayout = new HorizontalLayout(titleFilter, writerFilter, deweyFilter, amountFilter);

    public HomePageView(BookService bookService) {
        this.bookService = bookService;
        this.setSizeFull();;
        windowLayout.add(grid);
        windowLayout.setSizeFull();
        windowLayout.setHorizontalComponentAlignment(Alignment.CENTER, grid);
        grid.setWidth("1500px");
        grid.setHeight("600px");
        populateGrid();
        configureGrid();
        configureFilter();
        layoutCeption.add(filterLayout);
        layoutCeption.setHorizontalComponentAlignment(Alignment.CENTER, filterLayout);
        this.add(layoutCeption, windowLayout);
    }


    public void configureFilter()
    {
        availabilities.add(availabilityAll);
        availabilities.add(availabilityJa);
        availabilities.add(availabilityNo);

        amountFilter.setItemLabelGenerator(Availability::getOptions);
        amountFilter.setItems(availabilities);

        String[] filterStrings = new String[]{"titel", "författare", "placering"};
        TextField[] textFields = new TextField[]{titleFilter, writerFilter, deweyFilter};
        for(int i = 0; i < textFields.length; i++)
        {
            int finalI = i;
            textFields[i].setPlaceholder("Sök efter " + filterStrings[i] + " ...");
            textFields[i].setValueChangeMode(ValueChangeMode.EAGER);
            textFields[i].addValueChangeListener(e->grid.setItems(filterBy(filterStrings[finalI], textFields[finalI].getValue().toLowerCase())));
        }
        amountFilter.setPlaceholder("Sök efter tillgänglighet...");
        amountFilter.addValueChangeListener(e->grid.setItems(filterBy("utlånad", amountFilter.getValue().getOptions().toLowerCase())));
    }
    public List filterBy(String filter, String str)
    {
        List<VisitorBook> oldList = bookService.findVisitorBooks();
        ArrayList<VisitorBook> newList = new ArrayList<>();


        if(filter.equals("titel")) {
            for (VisitorBook book : oldList) {
                if (book.getTitle().toLowerCase().contains(str.toLowerCase())) {
                    newList.add(book);
                }
            }
        }
        else if(filter.equals("författare"))
        {
            for (VisitorBook book : oldList) {
                if (book.getWriter().toLowerCase().contains(str.toLowerCase())) {
                    newList.add(book);
                }
            }
        }
        else if(filter.equals("placering"))
        {
            for(VisitorBook book : oldList)
            {
                if(book.getDewey().toLowerCase().contains(str.toLowerCase()))
                {
                    newList.add(book);
                }
            }
        }
        else if(filter.equals("utlånad"))
        {
            for(VisitorBook book : oldList)
            {
                if (amountFilter.getValue().getOptions().equals("Tillgänglig")) {
                    str = "Ja";
                  }
                if(amountFilter.getValue().getOptions().equals("Alla"))
                {
                    newList.add(book);
                }
                if(book.getAmount().toLowerCase().contains(str.toLowerCase()))
                {
                    newList.add(book);
                }
            }
        }
        return newList;

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
        grid.getColumnByKey("title").setAutoWidth(true);
        grid.getColumnByKey("ebook").setAutoWidth(true);
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
