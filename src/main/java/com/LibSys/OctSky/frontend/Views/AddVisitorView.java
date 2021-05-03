package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.frontend.layouts.AdminLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "visitor", layout = AdminLayout.class)
@PageTitle("Besökare")
@CssImport("./views/about/about-view.css")
public class AddVisitorView extends Div {



    public AddVisitorView() {
        addClassName("Visitor-view");
        add(new Text("Här ska bibliotikarien kunna lägga till besökare med lånekort, vilket kräver FÖRNAMN, EFTERNAMN OCH PERSONNUMMER"));

    }
}