package com.LibSys.OctSky.frontend.layouts;

import com.LibSys.OctSky.frontend.Views.AddUserView;
import com.LibSys.OctSky.frontend.Views.AddVisitorView;
import com.LibSys.OctSky.frontend.Views.ArchivedBooksView;
import com.LibSys.OctSky.frontend.Views.ManageBookView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;

import java.util.Optional;

@JsModule("./styles/shared-styles.js")
@CssImport("./views/main/main-view.css")
public class VisitorLayout extends AppLayout {

    public VisitorLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());

    }

    private Component createHeaderContent() {
        H2 h1 = new H2("October Sky's Bibliotek");
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.add(h1);
        verticalLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, h1);
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(verticalLayout);
        layout.add(new Avatar());
        return layout;
    }

}
