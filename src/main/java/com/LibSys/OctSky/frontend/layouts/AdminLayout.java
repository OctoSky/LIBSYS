package com.LibSys.OctSky.frontend.layouts;

import com.LibSys.OctSky.backend.Service.StaffService;
import com.LibSys.OctSky.frontend.Views.AddUserView;
import com.LibSys.OctSky.frontend.Views.AddVisitorView;
import com.LibSys.OctSky.frontend.Views.ArchivedBooksView;
import com.LibSys.OctSky.frontend.Views.ManageBookView;
import com.LibSys.OctSky.frontend.Views.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Optional;

@JsModule("./styles/shared-styles.js")
@CssImport("./views/main/main-view.css")
public class AdminLayout extends AppLayout {

    private H1 viewTitle;
    private final Tabs menu;
    private StaffService staffService;

    public AdminLayout(StaffService staffService) {
        this.staffService = staffService;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        Avatar avatar = new Avatar();
        HorizontalLayout layout = new HorizontalLayout();
        Anchor logout = new Anchor("logout", "Logga ut");
        logout.setWidth("80px");
        avatar.setName(getFullName());
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(logout);
        layout.expand(viewTitle);
        layout.add(avatar);
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
                createTab("Hantera Användare", AddUserView.class),
                createTab("Hantera Besökare", AddVisitorView.class),
                createTab("Hantera Böcker", ManageBookView.class),
                createTab("Arkiverade Böcker", ArchivedBooksView.class),
                createTab("Lånade Böcker", BorrowedBookView.class)
        };
            }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
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
        else if(getUserRole().equals("[ROLE_MEMBER]"))
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
        String currentUserName = authentication.getName();
        String currentRole = authentication.getAuthorities().toString();
        return currentRole;
    }

}
