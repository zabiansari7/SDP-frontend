package de.srh.toolify.frontend.views.user;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Adminproducts")
@Route(value = "adminproducts", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class AdminProductsView extends Composite<VerticalLayout> {

    public AdminProductsView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        H4 h4 = new H4();
        H4 h42 = new H4();
        H4 h43 = new H4();
        H4 h44 = new H4();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        H5 h5 = new H5();
        H5 h52 = new H5();
        H5 h53 = new H5();
        H5 h54 = new H5();
        Button buttonSecondary = new Button();
        Button buttonSecondary2 = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("50px");
        h4.setText("Sr. No");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, h4);
        h4.setWidth("60px");
        h42.setText("Product Name");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, h42);
        h42.setWidth("300px");
        h43.setText("Quantity");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, h43);
        h43.setWidth("300px");
        h44.setText("Price");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, h44);
        h44.setWidth("320px");
        buttonPrimary.setText("Add Product");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.setHeight("25px");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("40px");
        layoutRow2.setAlignItems(Alignment.START);
        layoutRow2.setJustifyContentMode(JustifyContentMode.START);
        h5.setText("1");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h5);
        h5.setWidth("60px");
        h52.setText("Cordless Hammer Drill");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h52);
        h52.setWidth("300px");
        h53.setText("15");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h53);
        h53.setWidth("300px");
        h54.setText("€ 259.99");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h54);
        h54.setWidth("290px");
        buttonSecondary.setText("Edit");
        buttonSecondary.setWidth("min-content");
        buttonSecondary.setHeight("20px");
        buttonSecondary2.setText("Delete");
        buttonSecondary2.setWidth("min-content");
        buttonSecondary2.setHeight("20px");
        getContent().add(layoutRow);
        layoutRow.add(h4);
        layoutRow.add(h42);
        layoutRow.add(h43);
        layoutRow.add(h44);
        layoutRow.add(buttonPrimary);
        getContent().add(layoutRow2);
        layoutRow2.add(h5);
        layoutRow2.add(h52);
        layoutRow2.add(h53);
        layoutRow2.add(h54);
        layoutRow2.add(buttonSecondary);
        layoutRow2.add(buttonSecondary2);
    }
}
