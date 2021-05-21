package com.LibSys.OctSky.frontend.forms;

import com.LibSys.OctSky.backend.Service.BookService;
import com.LibSys.OctSky.backend.model.*;
import com.LibSys.OctSky.frontend.Views.ManageBookView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.textfield.TextField;
import java.awt.*;
import java.util.ArrayList;

public class ManageBookForm extends FormLayout {

    private TextField title = new TextField("Titel");
    private TextField price = new TextField("Pris");
    private TextField writer = new TextField("Författare");
    private TextField isbn = new TextField("ISBN");
    private TextField description = new TextField("Beskrivning");
    private TextField dewey = new TextField("Dewey");
    private IntegerField amount = new IntegerField("Antal");

    private ComboBox<Ebook> ebookComboBox = new ComboBox<Ebook>("E-bok");
    private ComboBox<Publisher> publisherComboBox = new ComboBox<Publisher>("Utgivare");
    private ComboBox<Category> categoryComboBox = new ComboBox<Category>("Kategori");

    private Ebook ebookJa = new Ebook("Ja");
    private Ebook ebookNej = new Ebook("Nej");
    private ArrayList<Ebook> ebooks = new ArrayList<>();

    private Button add = new Button("Lägg till");
    private Button clear = new Button("Rensa");
    private Button cancel = new Button("Avbryt");
    private Button save = new Button("Spara");

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    private Category category = new Category(0, "");
    private Publisher publisher = new Publisher(0, "");
    private Ebook ebook = new Ebook("");
    private Book book = new Book(0,"","","","","","","","",0,0,"", 0);
    Binder<Book> bookBinder = new Binder<>(Book.class);
    private AddBookObject addBookObject = new AddBookObject(book, publisher, category, ebook);
    Binder<AddBookObject> addBookObjectBinder = new Binder<>(AddBookObject.class);
    BookService bookService;
    ManageBookView manageBookView;

    public ManageBookForm(BookService bookService, ManageBookView manageBookView)
    {
        this.manageBookView = manageBookView;
        this.bookService = bookService;
        configureComboBoxes();
        configureBinder();
        configureButtons();
        add(title, price, writer, isbn, description, dewey, amount, ebookComboBox, publisherComboBox, categoryComboBox, buttonLayout);
    }

    public void configureBinder()
    {
        bookBinder.forField(title).bind(Book::getTitle, Book::setTitle);
        bookBinder.forField(price).bind(Book::getPrice, Book::setPrice);
        bookBinder.forField(writer).bind(Book::getWriter, Book::setWriter);
        bookBinder.forField(isbn).withValidator(isbn -> isbn.length() >= 10 , "Isbn måste vara minst 10 siffror").bind(Book::getIsbn, Book::setIsbn);
        bookBinder.forField(description).bind(Book::getDescription, Book::setDescription);
        bookBinder.forField(dewey).withValidator(dewey -> dewey.length() <=5,"Siffrorn måste vara mellan 0-999,och kan innehålla decimaler").bind(Book::getDewey, Book::setDewey);
        bookBinder.forField(amount).bind(Book::getAmount, Book::setAmount);
        addBookObjectBinder.forField(categoryComboBox).bind(AddBookObject::getCategory,AddBookObject::setCategory);
        addBookObjectBinder.forField(publisherComboBox).bind(AddBookObject::getPublisher, AddBookObject::setPublisher);
        addBookObjectBinder.forField(ebookComboBox).bind(AddBookObject::getEbook, AddBookObject::setEbook);
        bookBinder.setBean(book);
        addBookObjectBinder.setBean(addBookObject);
    }

    public void configureForm(FormState formState)
    {
        remove(title, price, writer, isbn, description, dewey, amount, ebookComboBox, publisherComboBox, categoryComboBox, buttonLayout);
        if(formState == FormState.Adding)
        {
            clearForm();
            buttonLayout = buttonsAdding();
        }
        else if(formState == FormState.Editing)
        {
            fillForm();
            buttonLayout = buttonsEditing();
        }
        else
        {
            this.setVisible(false);
        }
        add(title, price, writer, isbn, description, dewey, amount, ebookComboBox, publisherComboBox, categoryComboBox, buttonLayout);

    }
    public HorizontalLayout buttonsAdding()
    {
        return new HorizontalLayout(add, clear, cancel);
    }
    public HorizontalLayout buttonsEditing()
    {
        return new HorizontalLayout(save, clear, cancel);
    }
    public void fillForm()
    {
        book = manageBookView.getSelection();
        bookBinder.setBean(book);

        publisher = new Publisher(manageBookView.getSelection().getPublisherId(), manageBookView.getSelection().getPublisher());
        category = new Category(manageBookView.getSelection().getCategoryId(), manageBookView.getSelection().getCategory());
        ebook = new Ebook(manageBookView.getSelection().getEbook());

        addBookObject = new AddBookObject(book, publisher, category, ebook);

        addBookObjectBinder.setBean(addBookObject);
    }

    public void clearForm()
    {
        book = new Book(0, "","","","","","","","",0,0, "", 0);
        bookBinder.setBean(book);

        publisher = new Publisher(0, "");
        category = new Category(0, "");
        ebook = new Ebook("");
        addBookObject = new AddBookObject(book, publisher, category, ebook);

        addBookObjectBinder.setBean(addBookObject);
    }

    public void deleteBook(String reason){
        bookService.deleteBook(manageBookView.getSelection().getId(), reason);
        manageBookView.populateGrid();
    }

    public void addBook()
    {
        bookService.addnewbook(title.getValue(), writer.getValue(), isbn.getValue(), description.getValue(), price.getValue(), dewey.getValue(), publisherComboBox.getValue().getId(), categoryComboBox.getValue().getId(), ebookComboBox.getValue().getOption(), amount.getValue());
        manageBookView.populateGrid();
        this.setVisible(false);
    }

    public void saveBook()
    {
        bookService.savebook(manageBookView.getSelection().getId(), title.getValue(), writer.getValue(), isbn.getValue(), description.getValue(), price.getValue(), dewey.getValue(), publisherComboBox.getValue().getId(), categoryComboBox.getValue().getId(), ebookComboBox.getValue().getOption(), amount.getValue());
        manageBookView.populateGrid();
        this.setVisible(false);
    }

    public void configureComboBoxes()
    {
        ebooks.add(ebookJa);
        ebooks.add(ebookNej);
        ebookComboBox.setItems(ebooks);
        categoryComboBox.setItemLabelGenerator(Category::getName);
        categoryComboBox.setItems(bookService.findCategories());
        publisherComboBox.setItemLabelGenerator(Publisher::getName);
        publisherComboBox.setItems(bookService.findPublishers());
    }

    public void configureButtons()
    {
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add.addClickListener(e->addBook());
        cancel.addClickListener(e->this.setVisible(false));
        save.addClickListener(e->saveBook());
        clear.addClickListener(e->clearForm());
    }






}
