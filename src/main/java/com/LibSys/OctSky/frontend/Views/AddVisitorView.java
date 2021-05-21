package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.VisitorService;
import com.LibSys.OctSky.backend.model.BorrowedBook;
import com.LibSys.OctSky.backend.model.Reason;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.backend.model.Visitor;
import com.LibSys.OctSky.frontend.forms.AddVisitorForm;
import com.LibSys.OctSky.frontend.forms.FormState;
import com.LibSys.OctSky.frontend.layouts.AdminLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.ArrayList;
import java.util.List;

@Route(value = "visitor", layout = AdminLayout.class)
@PageTitle("Besökare")
@CssImport("./views/about/about-view.css")
@CssImport(value="./views/grid-cell.css", themeFor="vaadin-grid")
public class AddVisitorView extends VerticalLayout {

    Button addButton = new Button("Lägg till");
    Button removeButton = new Button("Ta bort");
    protected Grid<Visitor> grid = new Grid<>(Visitor.class);
    private VisitorService visitorService;
    private AddVisitorForm addVisitorForm;
    protected SingleSelect<Grid<Visitor>,Visitor> selection = grid.asSingleSelect();


    public AddVisitorView(VisitorService visitorService) {
        this.visitorService = visitorService;
        this.addVisitorForm = new AddVisitorForm(visitorService, this);

        setSizeFull();
        removeButton.setEnabled(false);

        addVisitorForm.setVisible(false);
        configureGrid();
        configureButtons();
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton,removeButton);
        populateGrid();
        add(buttonLayout,grid, addVisitorForm);


    }

    public void populateGrid(){
        grid.setItems(visitorService.findVisitor());

    }

    public void configureGrid(){

        grid.setColumns("roleId","visitorNumber","cardnumber","socialsecuritynumber","firstname","surname","email","phone","address");
        grid.getColumnByKey("cardnumber").setHeader("Kortnummer");
        grid.getColumnByKey("socialsecuritynumber").setHeader("Personnummer");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("surname").setHeader("Efternamn");
        grid.getColumnByKey("email").setHeader("E-postadress");
        grid.getColumnByKey("phone").setHeader("Telefonnr");
        grid.getColumnByKey("address").setHeader("Adress");
        grid.removeColumnByKey("roleId");
        grid.asSingleSelect().addValueChangeListener(event -> selectionHandler());
        grid.removeColumnByKey("visitorNumber");
        grid.addComponentColumn(item -> createDisableButton(item)).setKey("disable");
        grid.getColumnByKey("disable").setHeader("");


    }

    public Button createDisableButton(Visitor item)
    {
        Button returnButton = new Button();
        Reason reasonTheft = new Reason(4, "Stöld");
        Reason reasonLate = new Reason(5, "Försenade Böcker");
        Reason reasonLost = new Reason(6, "Förvunna Böcker");
        ArrayList<Reason> arrayList = new ArrayList<>();
        arrayList.add(reasonLate);
        arrayList.add(reasonTheft);
        arrayList.add(reasonLost);
        Notification notification = new Notification();
        Label label = new Label("Välj en anledning");
        ComboBox<Reason> reasonComboBox = new ComboBox<>();
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout buttonlayout = new HorizontalLayout();
        Button confirmbutton = new Button("Ok");
        confirmbutton.addClickListener(e-> {
                    visitorService.disableCard(item.getCardnumber(), reasonComboBox.getValue().getId());
                    populateGrid();
                });
        Button cancelButton = new Button("Avbryt");
        cancelButton.addClickListener(e->notification.close());

        if(item.getRoleId() == 3) {
            returnButton = new Button("Spärra", clickEvent ->
            {
                reasonComboBox.setItemLabelGenerator(Reason::getReason);
                reasonComboBox.setItems(arrayList);
                reasonComboBox.setAutofocus(true);
                notification.add(verticalLayout);
                verticalLayout.add(label, reasonComboBox, buttonlayout);
                buttonlayout.add(confirmbutton, cancelButton);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            });
        }
        else
        {
            returnButton = new Button("Ta bort spärr", clickEvent ->
            {
                visitorService.disableCard(item.getCardnumber(), 3);
                populateGrid();
            });
        }
        return returnButton;
    }

    public Visitor getSelection()
    {
        try
        {
            return selection.getValue();
        }
        catch (NullPointerException e)
        {
            return null;
        }

    }

    public void formVisibility(Boolean bool, FormState state) {
        addVisitorForm.setVisible(bool);
        addVisitorForm.configureForm(state);
    }

    public void selectionHandler()
    {
        if(selection.isEmpty())
        {
            removeButton.setEnabled(false);
            formVisibility(false, FormState.None);
        }
        else
        {
            removeButton.setEnabled(true);
            formVisibility(true, FormState.Editing);
        }
    }

    public void configureButtons(){
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        addButton.addClickListener(e -> formVisibility(true, FormState.Adding));
        removeButton.addClickListener(e -> addVisitorForm.deleteVisitor());
    }


}