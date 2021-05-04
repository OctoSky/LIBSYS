package com.LibSys.OctSky.frontend.forms;

import com.LibSys.OctSky.backend.Service.StaffService;
import com.LibSys.OctSky.backend.model.AddUserObject;
import com.LibSys.OctSky.backend.model.Credentials;
import com.LibSys.OctSky.backend.model.Roles;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.frontend.Views.AddUserView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class AddUserForm extends FormLayout {
    private StaffService staffService;
    private AddUserView addUserView;

    private TextField firstname = new TextField("Förnamn:");
    private TextField surname = new TextField("Efternamn:");
    private TextField phone = new TextField("Telefonnummer:");
    private TextField email = new TextField("E-post:");

    private ComboBox<Roles> rolesComboBox = new ComboBox<>("Befattning");
    private PasswordField passwordField = new PasswordField("Lösenord:");


    private Button saveButton = new Button("Spara");
    private Button clearButton = new Button("Rensa");
    private Button cancelButton = new Button("Avbryt");
    private Button addButton = new Button("Lägg till");
    protected HorizontalLayout buttonLayout = new HorizontalLayout();
    protected HorizontalLayout buttonLayoutAdding = new HorizontalLayout();

    private Binder<Staff> staffBinder = new Binder<>(Staff.class);
    private Binder<AddUserObject> addUserObjectBinder = new Binder<>(AddUserObject.class);

    private AddUserObject userObject = new AddUserObject(null, null);
    private Staff staffObject = new Staff(0,"","","","","");
    private Roles roleObject = new Roles(0, "");


    public AddUserForm(StaffService staffService, AddUserView addUserView) {
        this.staffService = staffService;
        this.addUserView = addUserView;
        configureBinder();
        configureComboBox();
        configureButtons();

    }

    public void configureForm(FormState formState)
    {
        remove(firstname, surname, phone, email, rolesComboBox, buttonLayout);
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
        add(firstname, surname, phone, email, rolesComboBox, buttonLayout);
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
        staffObject = addUserView.getSelection();
        roleObject = new Roles(0, addUserView.getSelection().getroles());
        staffBinder.setBean(staffObject);
        userObject = new AddUserObject(staffObject, roleObject);
        addUserObjectBinder.setBean(userObject);
    }
    public void clearForm()
    {
        staffObject = new Staff(0, "","","","","");
        roleObject = new Roles(0, "");
        userObject = new AddUserObject(null,null);
        staffBinder.setBean(staffObject);
        addUserObjectBinder.setBean(userObject);
    }
    public void configureButtons()
    {
        buttonLayout.add(saveButton, clearButton, cancelButton);
        buttonLayoutAdding.add(addButton, clearButton, cancelButton);
        clearButton.addClickListener(e->clearForm());
        cancelButton.addClickListener(e->this.setVisible(false));
        saveButton.addClickListener(e->saveStaff());
        addButton.addClickListener(e->addStaff());
    }
    public void saveStaff()
    {

    }

    public void addStaff(){
        staffService.addStaff(firstname.getValue(),surname.getValue(),phone.getValue(),email.getValue(),rolesComboBox.getValue().getRoleId(),"password");
        addUserView.populateGrid();
    }

    public void configureBinder() {
        staffBinder.forField(firstname).bind(Staff::getfirstname,Staff::setfirstname);
        staffBinder.forField(surname).bind(Staff::getsurname,Staff::setsurname);
        staffBinder.forField(phone).bind(Staff::getphone,Staff::setphone);
        staffBinder.forField(email).bind(Staff::getemail,Staff::setemail);

        addUserObjectBinder.forField(rolesComboBox).bind(AddUserObject::getRole, AddUserObject::setRole);
        //staffBinder.forField(passwordField).bind(Credentials::getPassword,Credentials::setPassword); //FIXME behöver ett sätt att göra detta säkert
        staffBinder.setBean(staffObject);
        addUserObjectBinder.setBean(userObject);
    }
    public void configureComboBox()
    {
        rolesComboBox.setItemLabelGenerator(Roles::getRoleName);
        rolesComboBox.setItems(staffService.findRoles());
    }
}
