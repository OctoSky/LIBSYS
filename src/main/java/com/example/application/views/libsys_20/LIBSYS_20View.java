package com.example.application.views.libsys_20;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "welcome", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("LIBSYS_2.0")
@CssImport("./views/libsys_20/l-ibsy-s20-view.css")
public class LIBSYS_20View extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public LIBSYS_20View() {
        addClassName("l-ibsy-s20-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
