package com.LibSys.OctSky.frontend.Views;


import com.LibSys.OctSky.backend.Service.StaffService;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.frontend.forms.AddUserForm;
import com.LibSys.OctSky.frontend.forms.FormState;
import com.LibSys.OctSky.frontend.layouts.AdminLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.util.List;

@Route(value = "user", layout = AdminLayout.class)

@PageTitle("Användare")
@CssImport("./views/about/about-view.css")
public class AddUserView extends VerticalLayout {

    Button addButton = new Button("Lägg till");
    Button removeButton = new Button("Ta bort");
    private StaffService staffService;
    private AddUserForm addUserForm;
    protected Grid<Staff> grid = new Grid<>(Staff.class);
    protected SingleSelect<Grid<Staff>,Staff> selection = grid.asSingleSelect();

    public AddUserView(StaffService staffService) {
        this.staffService = staffService;
        this.addUserForm = new AddUserForm(staffService, this);

        setSizeFull();
        removeButton.setEnabled(false);

        grid.setColumns("id","roles","firstname","surname","phone","email");
        grid.getColumnByKey("roles").setHeader("Befattning");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("surname").setHeader("Efternamn");
        grid.getColumnByKey("phone").setHeader("Telefonnummer");
        grid.getColumnByKey("email").setHeader("E-postadress");
        grid.removeColumnByKey("id");
        grid.asSingleSelect().addValueChangeListener(event -> selectionHandler());

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton,removeButton);
        configureButtons();
        populateGrid();
        addUserForm.setVisible(false);
        add(buttonLayout,grid, addUserForm);

    }

    public void formVisibility(Boolean bool, FormState state) {
        addUserForm.setVisible(bool);
        addUserForm.configureForm(state);
    }

    public void selectionHandler()
    {
        if(selection.isEmpty())
        {
            removeButton.setEnabled(false);
            formVisibility(false, FormState.None);
        }
        else
        {
            removeButton.setEnabled(true);
            formVisibility(true, FormState.Editing);
        }
    }
    public Staff getSelection()
    {
        return selection.getValue();
    }


    public void populateGrid(){
        grid.setItems(staffService.findStaff());
    }

    public void configureButtons(){

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addButton.addClickListener(Event-> formVisibility(true, FormState.Adding));
        removeButton.addClickListener(Event -> addUserForm.deleteStaff());

    }
}
