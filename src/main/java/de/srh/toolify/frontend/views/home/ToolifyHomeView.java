package de.srh.toolify.frontend.views.home;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.views.MainLayout;
import de.srh.toolify.frontend.views.products.ProductsView;

@PageTitle("Home | Toolify")
@Route(value = "home", layout = MainLayout.class)
public class ToolifyHomeView extends Composite<VerticalLayout> {

	private static final long serialVersionUID = 1L;
	HorizontalLayout layoutRow = new HorizontalLayout();
    Span badge = new Span();
    Button registerButton = new Button();
    Button loginButton = new Button();
    VerticalLayout layoutColumn2 = new VerticalLayout();
    
    ProductsView productsView;
    
    public ToolifyHomeView() {
    	RestClient client = new RestClient();
    	ResponseData resp = client.requestHttpToJsonNode("GET", "http://localhost:8080/private/admin/products/all", null, null);
    	JsonNode products = resp.getNode();
    	
    	getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        badge.setText("Badge");
        badge.getStyle().set("flex-grow", "1");
        badge.getElement().getThemeList().add("badge");
        registerButton.setText("Register");
        registerButton.setWidth("min-content");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(click -> UI.getCurrent().navigate("/register"));
        loginButton.setText("Log In");
        loginButton.setWidth("min-content");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClickListener(click -> UI.getCurrent().navigate("/login"));
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        getContent().add(layoutRow);
        //layoutRow.add(badge);
        layoutRow.add(registerButton);
        layoutRow.add(loginButton);
        layoutRow.setJustifyContentMode(JustifyContentMode.END);
        productsView = new ProductsView(products);
        layoutColumn2.add(productsView);
        getContent().add(layoutColumn2);

    }

}
