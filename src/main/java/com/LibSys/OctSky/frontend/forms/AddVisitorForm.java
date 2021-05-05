package com.LibSys.OctSky.frontend.forms;

import com.LibSys.OctSky.backend.Service.StaffService;
import com.LibSys.OctSky.backend.Service.VisitorService;
import com.LibSys.OctSky.backend.model.Visitor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.awt.*;

public class AddVisitorForm extends FormLayout {


    private Button addButton = new Button("Lägg till");
    private Button clearButton = new Button("Rensa");
    private Button cancelButton = new Button("Avbryt");
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private VisitorService visitorService;
    private TextField socialSecurityNumber_Field = new TextField("Personnummer");
    private TextField firstName_Field = new TextField("Förnamn");
    private TextField surName_Field = new TextField("Efternamn");
    private Binder<Visitor> visitorBinder = new Binder<>(Visitor.class);
    private Visitor visitor = new Visitor(0,"", "", "");
    public AddVisitorForm(VisitorService visitorService){
        configureBinder();
        configureButtons();
        buttonLayout.add(addButton,clearButton,cancelButton);
        add(socialSecurityNumber_Field,firstName_Field,surName_Field,buttonLayout);



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
        cancelButton.addClickListener(e -> this.setVisible(false));
    }

    public void configureForm(){

    }
}
