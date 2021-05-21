package com.LibSys.OctSky.frontend.layouts;

import com.LibSys.OctSky.backend.Security.SecurityUtils;
import com.LibSys.OctSky.backend.Service.StaffService;
import com.LibSys.OctSky.frontend.Views.AddUserView;
import com.LibSys.OctSky.frontend.Views.AddVisitorView;
import com.LibSys.OctSky.frontend.Views.ArchivedBooksView;
import com.LibSys.OctSky.frontend.Views.ManageBookView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Optional;

@JsModule("./styles/shared-styles.js")
@CssImport("./views/main/main-view.css")
public class VisitorLayout extends AppLayout {
    StaffService staffService;
    public VisitorLayout(StaffService staffService) {
        this.staffService = staffService;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
    }

    private Component createHeaderContent() {
        Avatar avatar = new Avatar();
        Anchor logout = new Anchor("logout", " Logga ut");
        Anchor login = new Anchor("login", " Logga in");
        Anchor adminpanel = new Anchor("user", "Adminpanel |");
        adminpanel.setWidth("120px");
        Anchor librarianpanel = new Anchor("visitor","Adminpanel |");
        librarianpanel.setWidth("120px");
        VerticalLayout fillLayout = new VerticalLayout();
        fillLayout.setWidth("160px");
        login.setWidth("80px");
        logout.setWidth("80px");
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
        layout.add(fillLayout);
        layout.add(verticalLayout);
       if(getUserRole().equals("[ROLE_ADMIN]"))
       {
           layout.add(adminpanel);
       }
       if(getUserRole().equals("[ROLE_LIBRARIAN]"))
       {
           layout.add(librarianpanel);
       }
        if(isUserLoggedIn())
        {
            layout.add(logout);
        }
        else
        {
            layout.add(login);
        }
        avatar.setName(getFullName());
        System.out.println(getFullName());
        layout.add(avatar);
        return layout;
    }

    public String getFullName()
    {
        String name = "";
        if(getUserRole().equals("[ROLE_ADMIN]") || getUserRole().equals("[ROLE_LIBRARIAN]")) {
            Map<String, Object> out = staffService.searchUsersWithEmail(getUserName());
            String firstname = (String) out.get("firstname_out");
            String surname = (String) out.get("surname_out");
            name =  firstname + " " + surname;
        }
        else if(getUserRole().equals("[ROLE_MEMBER]") || getUserRole().equals("[ROLE_MEMBER_DISABLE_THEFT]") ||getUserRole().equals("[ROLE_MEMBER_DISABLE_LATE]") || getUserRole().equals("[ROLE_MEMBER_DISABLE_LOST]"))
        {
            Map<String, Object> out2 = staffService.searchUsersWithCard(getUserNumber());
            String firstname = (String) out2.get("firstname_out");
            String surname = (String) out2.get("surname_out");
            name = firstname + " " + surname;
        }
        return name;
    }

    public String getUserName()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return currentUserName;
    }
    public int getUserNumber()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        int cardNumber = Integer.parseInt(currentUserName.trim());
        return cardNumber;
    }
    public String getUserRole()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentRole = authentication.getAuthorities().toString();
        return currentRole;
    }

    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = false;
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            isLoggedIn = true;
        }
        return isLoggedIn;
    }

}
