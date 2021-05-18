package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.BookService;
import com.LibSys.OctSky.backend.model.Book;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.backend.model.Visitor;
import com.LibSys.OctSky.frontend.forms.FormState;
import com.LibSys.OctSky.frontend.forms.ManageBookForm;
import com.LibSys.OctSky.frontend.layouts.AdminLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import javax.xml.validation.ValidatorHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Route(value = "books", layout = AdminLayout.class)
@PageTitle("Böcker")
@CssImport("./views/about/about-view.css")
public class ManageBookView extends VerticalLayout {
    BookService bookService;
    ManageBookForm manageBookForm;
    Grid<Book> grid = new Grid<>(Book.class);

    TextField categoryFilter = new TextField();
    TextField titleFilter = new TextField();
    TextField isbnFilter = new TextField();
    TextField writerFilter = new TextField();

    HorizontalLayout filterLayout = new HorizontalLayout(titleFilter, writerFilter, isbnFilter, categoryFilter);

    Button add = new Button("Lägg till");
    Button remove = new Button("Ta bort");

    protected SingleSelect<Grid<Book>,Book> selection = grid.asSingleSelect();
    protected HorizontalLayout buttonLayout = new HorizontalLayout();

    public ManageBookView(BookService bookService)
    {
        this.bookService = bookService;
        manageBookForm = new ManageBookForm(bookService, this);
        manageBookForm.setVisible(false);
        this.setSizeFull();
        remove.setEnabled(false);
        configureFilter();
        configureButtons();
        populateGrid();
        configureGrid();
        add(buttonLayout, filterLayout, grid, manageBookForm);
    }

    public void configureFilter()
    {
        String[] filterStrings = new String[]{"titel", "författare", "isbn", "kategori"};
        TextField[] textFields = new TextField[]{titleFilter, writerFilter, isbnFilter, categoryFilter};
        for(int i = 0; i < textFields.length; i++)
        {
            int finalI = i;
            textFields[i].setPlaceholder("Sök efter " + filterStrings[i] + " ...");
            textFields[i].setValueChangeMode(ValueChangeMode.EAGER);
            textFields[i].addValueChangeListener(e->grid.setItems(filterBy(filterStrings[finalI], textFields[finalI].getValue().toLowerCase())));
        }
    }

    public List filterBy(String filter, String str)
    {
        List<Book> oldList = bookService.findBooks();
        ArrayList<Book> newList = new ArrayList<>();


        if(filter.equals("kategori")) {
            for (Book book : oldList) {
                if (book.getCategory().toLowerCase().contains(str.toLowerCase())) {
                    newList.add(book);
                }
            }
        }
        else if(filter.equals("isbn"))
        {
            for (Book book : oldList) {
                if (book.getIsbn().toLowerCase().contains(str.toLowerCase())) {
                    newList.add(book);
                }
            }
        }
        else if(filter.equals("titel"))
        {
            for(Book book : oldList)
            {
                if(book.getTitle().toLowerCase().contains(str.toLowerCase()))
                {
                    newList.add(book);
                }
            }
        }
        else if(filter.equals("författare"))
        {
            for(Book book : oldList)
            {
                if(book.getWriter().toLowerCase().contains(str.toLowerCase()))
                {
                    newList.add(book);
                }
            }
        }
        return newList;

    }

    public void populateGrid()
    {
        grid.setItems(bookService.findBooks());
    }

    public void configureButtons()
    {
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        remove.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add.addClickListener(Event-> formVisibility(true, FormState.Adding));
        remove.addClickListener(Event -> deleteBook());
        buttonLayout.add(add,remove);
    }

    public void configureGrid()
    {
        grid.setColumns("id","title", "writer", "description", "price", "isbn", "dewey", "category", "publisher","categoryId","publisherId", "ebook", "amount");
        grid.getColumnByKey("title").setHeader("Titel");
        grid.getColumnByKey("writer").setHeader("Författare");
        grid.getColumnByKey("price").setHeader("Pris");
        grid.getColumnByKey("isbn").setHeader("ISBN");
        grid.getColumnByKey("dewey").setHeader("Dewey");
        grid.getColumnByKey("category").setHeader("Kategori");
        grid.getColumnByKey("publisher").setHeader("Förlag");
        grid.getColumnByKey("ebook").setHeader("E-bok");
        grid.getColumnByKey("amount").setHeader("Antal");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("description");
        grid.removeColumnByKey("categoryId");
        grid.removeColumnByKey("publisherId");
        grid.addComponentColumn(item -> createDescriptionButton(item))
                        .setKey("beskrivning");
        grid.asSingleSelect().addValueChangeListener(event -> selectionHandler());
        grid.getColumnByKey("beskrivning").setHeader("Beskrivning");
        grid.getColumnByKey("title").setWidth("200px");
        grid.getColumnByKey("price").setWidth("30px");
    }



    public Button createDescriptionButton(Book item)
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

    public void deleteBook()
    {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Notification notification = new Notification();
        Button button = new Button("Okej");
        Button cancelbutton = new Button("Avbryt");
        TextField reason = new TextField("");
        Label label = new Label("Skriv en anledning i textfältet");
        button.addClickListener(event ->
        {
            if(!reason.isEmpty()) {
                notification.close();
                manageBookForm.deleteBook(reason.getValue());
            }
            else
            {
                label.setText("Fältet kan inte lämnas blankt!");
            }
        });
        cancelbutton.addClickListener(event -> notification.close());
        buttonLayout.add(button,cancelbutton);
        verticalLayout.setSizeFull();
        verticalLayout.add(label, reason, buttonLayout);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.add(verticalLayout);
        notification.setVisible(true);
        notification.open();
    }
    public void formVisibility(Boolean bool, FormState state) {
        manageBookForm.setVisible(bool);
        manageBookForm.configureForm(state);
    }



    public void selectionHandler()
    {
        if(selection.isEmpty())
        {
            remove.setEnabled(false);
            formVisibility(false, FormState.None);
        }
        else
        {
            remove.setEnabled(true);
            formVisibility(true, FormState.Editing);
        }
    }
    public Book getSelection()
    {
        return selection.getValue();
    }


}
