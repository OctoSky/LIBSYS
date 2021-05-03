package com.LibSys.OctSky.frontend.forms;

import com.LibSys.OctSky.backend.model.Credentials;
import com.LibSys.OctSky.backend.model.Roles;
import com.LibSys.OctSky.backend.model.Staff;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AddUserForm extends FormLayout {

    private TextField firstname = new TextField("Förnamn:");
    private TextField surname = new TextField("Efternamn:");
    private TextField phone = new TextField("Telefonnummer:");
    private TextField email = new TextField("E-post:");
    private ComboBox<Roles> rolesComboBox = new ComboBox<>("Befattning");
    private PasswordField passwordField = new PasswordField("Lösenord:");
    private Button saveButton = new Button("Spara");
    private Button clearButton = new Button("Rensa");
    private Binder<Staff> staffBinder = new Binder<>(Staff.class);

    public AddUserForm() {
    }

    public void configureBinder() {
        staffBinder.forField(firstname).bind(Staff::getfirstname,Staff::setfirstname);
        staffBinder.forField(surname).bind(Staff::getsurname,Staff::setsurname);
        staffBinder.forField(phone).bind(Staff::getphone,Staff::setphone);
        staffBinder.forField(email).bind(Staff::getemail,Staff::setemail);
        staffBinder.forField(firstname).bind(Staff::getfirstname,Staff::setfirstname);
        staffBinder.forField(rolesComboBox).bind(Roles::getRoleName,Roles::setRoleName);
        //staffBinder.forField(passwordField).bind(Credentials::getPassword,Credentials::setPassword); //FIXME behöver ett sätt att göra detta säkert
    }
}
