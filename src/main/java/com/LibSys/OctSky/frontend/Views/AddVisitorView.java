package com.LibSys.OctSky.frontend.Views;

import com.LibSys.OctSky.backend.Service.VisitorService;
import com.LibSys.OctSky.backend.model.Staff;
import com.LibSys.OctSky.backend.model.Visitor;
import com.LibSys.OctSky.frontend.forms.AddVisitorForm;
import com.LibSys.OctSky.frontend.layouts.AdminLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "visitor", layout = AdminLayout.class)
@PageTitle("Besökare")
@CssImport("./views/about/about-view.css")
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

        grid.setColumns("visitorNumber","cardnumber","socialsecuritynumber","firstname","surname");
        grid.getColumnByKey("cardnumber").setHeader("Kortnummer");
        grid.getColumnByKey("socialsecuritynumber").setHeader("Personnummer");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("surname").setHeader("Efternamn");
        grid.removeColumnByKey("visitorNumber");


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
    public void configureButtons(){
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        addButton.addClickListener(e -> addVisitorForm.setVisible(true));
        removeButton.addClickListener(e -> addVisitorForm.deleteVisitor());
    }


}