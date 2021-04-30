package com.LibSys.OctSky.views.Users;


import com.LibSys.OctSky.layouts.AdminLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = AdminLayout.class)
@PageTitle("User")
@CssImport("./views/about/about-view.css")
public class AddUserView extends Div {

    //private Grid<> grid = new Grid()

    public AddUserView() {
        addClassName("user-view");


    }
}
