package com.example.application.views.Users;


import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
@PageTitle("User")
@CssImport("./views/about/about-view.css")
public class AddUserView extends Div {



    public AddUserView() {
        addClassName("user-view");
        add(new Text("Här kommer bibliotekschefen kunna lägga till nya användare"));

    }
}
