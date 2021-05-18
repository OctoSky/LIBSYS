package com.LibSys.OctSky.frontend.forms;

import com.LibSys.OctSky.backend.Service.VisitorService;
import com.LibSys.OctSky.backend.model.AddUserObject;
import com.LibSys.OctSky.backend.model.Roles;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.backend.model.Visitor;
import com.LibSys.OctSky.frontend.Views.AddVisitorView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import org.apache.commons.lang3.StringUtils;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.util.List;

public class AddVisitorForm extends FormLayout {

    private List<Visitor> visitorList;

    private Button addButton = new Button("Lägg till");
    private Button saveButton = new Button("Spara");
    private Button clearButton = new Button("Rensa");
    private Button cancelButton = new Button("Avbryt");
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private Notification notify = new Notification();

    private VisitorService visitorService;
    private AddVisitorView addVisitorView;

    private TextField socialSecurityNumber_Field = new TextField("Personnummer");
    private TextField firstName_Field = new TextField("Förnamn");
    private TextField surName_Field = new TextField("Efternamn");
    private EmailField email_Field = new EmailField("E-postadress");
    private TextField phone_Field = new TextField("Telefonnr");
    private TextField address_Field = new TextField("Adress");

    private Binder<Visitor> visitorBinder = new Binder<>(Visitor.class);
    private Visitor visitor = new Visitor(0, 0,"", "", "",""," ", "");

    public AddVisitorForm(VisitorService visitorService, AddVisitorView addVisitorView)
    {
        this.addVisitorView = addVisitorView;
        this.visitorService = visitorService;

        configureBinder();
        configureButtons();
        configureFields();

        add(socialSecurityNumber_Field,firstName_Field,surName_Field,email_Field, phone_Field, address_Field, buttonLayout);

    }

    public void closeNotification()
    {
        notify.close();
        addButton.setEnabled(true);
    }

    public void addNotification(String message)
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label label = new Label(message);
        label.setWidthFull();
        notify = new Notification();
        notify.setPosition(Notification.Position.MIDDLE);
        Button notificationButton = new Button("Close");
        horizontalLayout.add(label, notificationButton);
        notificationButton.addClickListener(event -> closeNotification());
        notify.add(horizontalLayout);
        notificationButton.setVisible(true);
        notify.open();
    }
    public void addVisitor()
    {
        boolean duplicateSSN = false;
        visitorList = visitorService.findVisitor();
        for(Visitor visitor: visitorList)
        {
            if(visitor.getSocialsecuritynumber().equals(socialSecurityNumber_Field.getValue()))
            {
                duplicateSSN = true;
            }
        }
        visitorList = visitorService.findVisitor();
        if(!duplicateSSN) {
            if (StringUtils.isNumeric(phone_Field.getValue()) && StringUtils.isNumeric(socialSecurityNumber_Field.getValue())) {
                visitorService.addVisitor(socialSecurityNumber_Field.getValue(),
                        firstName_Field.getValue(),
                        surName_Field.getValue(),
                        email_Field.getValue(),
                        phone_Field.getValue(),
                        address_Field.getValue(),
                        "enc_password_super_secure123");
            }
            else if(!StringUtils.isNumeric(phone_Field.getValue()))
            {
                addNotification("Ett telefonnummer kan endast bestå av siffror!");
                addButton.setEnabled(false);
            }
            else if(!StringUtils.isNumeric(socialSecurityNumber_Field.getValue()))
            {
                addNotification("Ett personnummer kan endast bestå av siffror!");
                addButton.setEnabled(false);
            }
        }
        else
        {
            addNotification("Personnummer finns redan, försök med ett annat!");
            addButton.setEnabled(false);
        }

        addVisitorView.populateGrid();
    }



    public void configureForm(FormState formState)
    {
        remove(socialSecurityNumber_Field,firstName_Field,surName_Field,email_Field, phone_Field, address_Field, buttonLayout);
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
        add(socialSecurityNumber_Field,firstName_Field,surName_Field,email_Field, phone_Field, address_Field, buttonLayout);
    }

    public HorizontalLayout buttonsAdding()
    {
        return new HorizontalLayout(addButton, clearButton, cancelButton);
    }
    public HorizontalLayout buttonsEditing()
    {
        return new HorizontalLayout(saveButton, clearButton, cancelButton);
    }
    public void fillForm()
    {
        visitor = addVisitorView.getSelection();
        visitorBinder.setBean(visitor);
    }

    public void clearForm()
    {
        Visitor visitor = new Visitor(0, 0,"", "", "", "", "", "");
        visitorBinder.setBean(visitor);

    }

    public void configureBinder(){
        visitorBinder.forField(socialSecurityNumber_Field).withValidator(socialSecurityNumber_Field -> socialSecurityNumber_Field.length() >= 10, "Personnummer måste vara tio siffror långt").bind(Visitor::getSocialsecuritynumber,Visitor::setSocialsecuritynumber);
        visitorBinder.forField(firstName_Field).withValidator(firstName_Field -> firstName_Field.length() >2,"Förnamn måste vara minst två tecken").bind(Visitor::getFirstname,Visitor::setFirstname);
        visitorBinder.forField(surName_Field).withValidator(surName_Field ->surName_Field.length() > 2,"Efternamn måste vara minst två tecken").bind(Visitor::getSurname,Visitor::setSurname);
        visitorBinder.forField(email_Field).withValidator(new EmailValidator("Det här är inte en giltig E-post address")).bind(Visitor::getEmail, Visitor::setEmail);

        visitorBinder.forField(phone_Field).withValidator(phone_Field -> phone_Field.length() >= 10 ,"Telefonnummer ska vara tio siffror långt").bind(Visitor::getPhone, Visitor::setPhone);
        visitorBinder.forField(address_Field).bind(Visitor::getAddress, Visitor::setAddress);

        visitorBinder.setBean(visitor);
    }

    public void configureButtons(){
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addButton.addClickListener(e -> addVisitor());
        saveButton.addClickListener(e -> saveVisitor());
        clearButton.addClickListener(e -> clearForm());
        cancelButton.addClickListener(e -> this.setVisible(false));
    }

    public void saveVisitor()
    {
        visitorService.savevisitor(addVisitorView.getSelection().getVisitorNumber(),socialSecurityNumber_Field.getValue(),firstName_Field.getValue(), surName_Field.getValue(), email_Field.getValue(), phone_Field.getValue(), address_Field.getValue(), "enc_password_super_secure123");
        addVisitorView.populateGrid();
    }

    public void deleteVisitor()
    {
        visitorService.deleteVisitor(addVisitorView.getSelection().getVisitorNumber());
        addVisitorView.populateGrid();
    }

    public void configureFields()
    {
        email_Field.setClearButtonVisible(true);
        email_Field.setErrorMessage("Vänligen fyll i en giltig E-postadress");
        phone_Field.setMaxLength(11);
        socialSecurityNumber_Field.setMaxLength(12);
    }
}
