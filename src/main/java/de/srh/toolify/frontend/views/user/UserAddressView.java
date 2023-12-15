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
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("UserAddress")
@Route(value = "useraddress", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class UserAddressView extends Composite<VerticalLayout> {

    public UserAddressView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        H4 h4 = new H4();
        H4 h42 = new H4();
        H4 h43 = new H4();
        H4 h44 = new H4();
        H4 h45 = new H4();
        Button buttonPrimary = new Button();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        H5 h5 = new H5();
        H5 h52 = new H5();
        H5 h53 = new H5();
        H5 h54 = new H5();
        H5 h55 = new H5();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().setHeight("752px");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("50px");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.START);
        h4.setText("Sr.No");
        h4.setWidth("98px");
        h4.setHeight("29px");
        h42.setText("Street Name");
        h42.setWidth("239px");
        h42.setHeight("29px");
        h43.setText("Street Number");
        h43.setWidth("157px");
        h43.setHeight("29px");
        h44.setText("Postcode");
        h44.setWidth("166px");
        h44.setHeight("29px");
        h45.setText("City");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, h45);
        h45.setWidth("327px");
        h45.setHeight("29px");
        buttonPrimary.setText("Add Address");
        buttonPrimary.getStyle().set("flex-grow", "1");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("55px");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.START);
        h5.setText("1");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h5);
        h5.setWidth("98px");
        h5.setHeight("50px");
        h52.setText("behram");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h52);
        h52.setWidth("239px");
        h52.setHeight("50px");
        h53.setText("18");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h53);
        h53.setWidth("157px");
        h53.setHeight("50px");
        h54.setText("400051");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, h54);
        h54.setWidth("166px");
        h54.setHeight("50px");
        h55.setText("Mumbai");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.END, h55);
        h55.setWidth("327px");
        h55.setHeight("55px");
        buttonSecondary.setText("Delete Address");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, buttonSecondary);
        buttonSecondary.setWidth("min-content");
        buttonSecondary.setHeight("20px");
        getContent().add(layoutRow);
        layoutRow.add(h4);
        layoutRow.add(h42);
        layoutRow.add(h43);
        layoutRow.add(h44);
        layoutRow.add(h45);
        layoutRow.add(buttonPrimary);
        getContent().add(layoutRow2);
        layoutRow2.add(h5);
        layoutRow2.add(h52);
        layoutRow2.add(h53);
        layoutRow2.add(h54);
        layoutRow2.add(h55);
        layoutRow2.add(buttonSecondary);
    }
}

