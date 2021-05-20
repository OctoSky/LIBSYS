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

    private H1 viewTitle;
    public VisitorLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());

    }

    private Component createHeaderContent() {
        H1 h1 = new H1("Bibliotek");
        h1.setWidth("200px");
        h1.setHeight("200px");
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
        viewTitle = new H1();
        layout.add(verticalLayout);
        layout.add(new Avatar());
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "LIBSYS_2.0 logo"));
        logoLayout.add(new H1("LIBSYS_2.0"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }


    private Component[] createMenuItems() {
        return new Tab[]{
                createTab("Hantera Användare", AddUserView.class), //TODO rätt flikar, rätt views
                createTab("Hantera Besökare", AddVisitorView.class),
                createTab("Hantera Böcker", ManageBookView.class),
                createTab("Arkiverade Böcker", ArchivedBooksView.class)
        };
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

}
