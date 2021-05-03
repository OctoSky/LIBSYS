package com.LibSys.OctSky.views.Users;


import com.LibSys.OctSky.backend.Service.StaffService;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.layouts.AdminLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = AdminLayout.class)
@PageTitle("Användare")
@CssImport("./views/about/about-view.css")
public class AddUserView extends Div {

    private StaffService staffService;
    Grid<Staff> grid = new Grid<>(Staff.class);

    public AddUserView(StaffService staffService) {
        this.staffService = staffService;

        grid.setColumns("id","roles","firstname","surname","phone","email");
        grid.getColumnByKey("roles").setHeader("Befattning");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("surname").setHeader("Efternamn");
        grid.getColumnByKey("phone").setHeader("Telefonnummer");
        grid.getColumnByKey("email").setHeader("E-postadress");
        grid.removeColumnByKey("id");
        add(grid);
        populateGrid();


    }

    public void populateGrid(){
        grid.setItems(staffService.findStaff());

    }

}
