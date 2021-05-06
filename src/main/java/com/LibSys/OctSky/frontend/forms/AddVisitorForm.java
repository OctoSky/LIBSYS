package com.LibSys.OctSky.frontend.forms;

import com.LibSys.OctSky.backend.Service.VisitorService;
import com.LibSys.OctSky.backend.model.Visitor;
import com.LibSys.OctSky.frontend.Views.AddVisitorView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AddVisitorForm extends FormLayout {


    private Button addButton = new Button("Lägg till");
    private Button clearButton = new Button("Rensa");
    private Button cancelButton = new Button("Avbryt");
    private HorizontalLayout buttonLayout = new HorizontalLayout();

    private VisitorService visitorService;
    private AddVisitorView addVisitorView;

    private TextField socialSecurityNumber_Field = new TextField("Personnummer");
    private TextField firstName_Field = new TextField("Förnamn");
    private TextField surName_Field = new TextField("Efternamn");

    private Binder<Visitor> visitorBinder = new Binder<>(Visitor.class);
    private Visitor visitor = new Visitor(0, 0,"", "", "");

    public AddVisitorForm(VisitorService visitorService, AddVisitorView addVisitorView){
        this.addVisitorView = addVisitorView;
        this.visitorService = visitorService;
        configureBinder();
        configureButtons();
        buttonLayout.add(addButton,clearButton,cancelButton);
        add(socialSecurityNumber_Field,firstName_Field,surName_Field,buttonLayout);


    }

    public void addVisitor()
    {
        visitorService.addVisitor(socialSecurityNumber_Field.getValue(), firstName_Field.getValue(), surName_Field.getValue(), "enc_password_super_secure123");
        addVisitorView.populateGrid();
    }

    public void configureBinder(){
        visitorBinder.forField(socialSecurityNumber_Field).bind(Visitor::getSocialsecuritynumber,Visitor::setSocialsecuritynumber);
        visitorBinder.forField(firstName_Field).bind(Visitor::getFirstname,Visitor::setFirstname);
        visitorBinder.forField(surName_Field).bind(Visitor::getSurname,Visitor::setSurname);
        visitorBinder.setBean(visitor);
    }

    public void configureButtons(){
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addButton.addClickListener(e -> addVisitor());
        clearButton.addClickListener(e -> clearForm());
        cancelButton.addClickListener(e -> this.setVisible(false));
    }

    public void clearForm()
    {
        Visitor visitor = new Visitor(0, 0,"", "", "");
        visitorBinder.setBean(visitor);

    }

    public void deleteVisitor()
    {
        visitorService.deleteVisitor(addVisitorView.getSelection().getVisitorNumber());
        addVisitorView.populateGrid();
    }

    public void configureForm(){

    }
}
