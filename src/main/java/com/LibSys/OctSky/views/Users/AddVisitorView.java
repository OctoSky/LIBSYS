package com.LibSys.OctSky.views.Users;

import com.LibSys.OctSky.layouts.AdminLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "visitor", layout = AdminLayout.class)
@PageTitle("Visitor")
@CssImport("./views/about/about-view.css")
public class AddVisitorView extends Div {



    public AddVisitorView() {
        addClassName("Visitor-view");
        add(new Text("Här ska bibliotikarien kunna lägga till besökare med lånekort, vilket kräver FÖRNAMN, EFTERNAMN OCH PERSONNUMMER"));

    }
}