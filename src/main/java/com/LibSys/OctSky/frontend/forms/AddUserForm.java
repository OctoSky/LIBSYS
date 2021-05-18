package com.LibSys.OctSky.frontend.forms;

import com.LibSys.OctSky.backend.Service.StaffService;
import com.LibSys.OctSky.backend.model.AddUserObject;
import com.LibSys.OctSky.backend.model.Roles;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.frontend.Views.AddUserView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;

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
    private Staff staffObject = new Staff(0,0,"","","","");
    private Roles roleObject = new Roles(0, "");

    public AddUserForm(StaffService staffService, AddUserView addUserView) {
        this.staffService = staffService;
        this.addUserView = addUserView;

        phone.setMaxLength(10);

        configureBinder();
        configureComboBox();
        configureButtons();

    }

    public void configureForm(FormState formState)
    {
        remove(firstname, surname, phone, email, rolesComboBox, passwordField, buttonLayout);
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
        add(firstname, surname, phone, email, rolesComboBox, passwordField, buttonLayout);
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
        roleObject = rolesListSwedish().get(addUserView.getSelection().getRoleId()-1);
        staffBinder.setBean(staffObject);
        userObject = new AddUserObject(staffObject, roleObject);
        addUserObjectBinder.setBean(userObject);
    }

    public void clearForm()
    {
        staffObject = new Staff(0, 0,"","","","");
        roleObject = new Roles(0, "");
        userObject = new AddUserObject(null,null);
        staffBinder.setBean(staffObject);
        addUserObjectBinder.setBean(userObject);
    }

    public void configureButtons()
    {
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        buttonLayout.add(saveButton, clearButton, cancelButton);
        buttonLayoutAdding.add(addButton, clearButton, cancelButton);

        clearButton.addClickListener(e->clearForm());
        cancelButton.addClickListener(e->this.setVisible(false));
        saveButton.addClickListener(e->saveStaff());
        addButton.addClickListener(e->addStaff());
    }

    public void saveStaff()
    {
        staffService.savestaff(addUserView.getSelection().getId(),
                firstname.getValue(),
                surname.getValue(),
                phone.getValue(),
                email.getValue(),
                rolesComboBox.getValue().getRoleId(),
                passwordField.getValue(),
                "enc_password_super_secure123");
        addUserView.populateGrid();
    }

    public void deleteStaff(){
        staffService.deleteStaff(addUserView.getSelection().getId());
        addUserView.populateGrid();
    }

    public void addStaff(){
        staffService.addStaff(firstname.getValue(),surname.getValue(),phone.getValue(),email.getValue(),rolesComboBox.getValue().getRoleId(),passwordField.getValue(), "YaBoiKa");
        addUserView.populateGrid();
        this.setVisible(false);
    }

    public void configureBinder() {
        staffBinder.forField(firstname).withValidator( firstname -> firstname.length() >= 2, "Förnamn måste vara minst två tecken").bind(Staff::getfirstname,Staff::setfirstname);
        staffBinder.forField(surname).withValidator(surname -> surname.length() >= 2,"Efternamn måste vara minst två tecken").bind(Staff::getsurname,Staff::setsurname);
        staffBinder.forField(phone).withValidator(phone -> phone.length() >= 10,"Telefonnummer ska vara tio siffror långt").bind(Staff::getphone,Staff::setphone);
        staffBinder.forField(email).withValidator(new EmailValidator("Det här är inte en giltig E-post address")).bind(Staff::getemail,Staff::setemail);


        addUserObjectBinder.forField(rolesComboBox).asRequired("Måste ha en befattning").bind(AddUserObject::getRole, AddUserObject::setRole);
        //staffBinder.forField(passwordField).bind(Credentials::getPassword,Credentials::setPassword); //FIXME behöver ett sätt att göra detta säkert
        staffBinder.setBean(staffObject);
        addUserObjectBinder.setBean(userObject);
    }

    public void configureComboBox()
    {
        rolesComboBox.setItemLabelGenerator(Roles::getRoleName);
        rolesComboBox.setItems(rolesListSwedish());
    }

    public List<Roles> rolesListSwedish()
    {
        List<Roles> newList = staffService.findRoles();
        newList.get(0).setRoleName("Administratör");
        newList.get(0).setRoleId(1);
        newList.get(1).setRoleName("Bibliotekarie");
        newList.get(1).setRoleId(2);
        newList.get(2).setRoleName("Kund");
        newList.get(2).setRoleId(3);
        return newList;
    }
}
