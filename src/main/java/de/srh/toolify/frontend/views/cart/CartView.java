package de.srh.toolify.frontend.views.cart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Product;
import de.srh.toolify.frontend.data.PurchaseItem;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Toolify | Cart")
@Route(value = "cart", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class CartView extends Composite<VerticalLayout> implements HasUrlParameter<Long>{

    private static final long serialVersionUID = -8245968777291604244L;
    
    VerticalLayout layoutColumn2 = new VerticalLayout();
    VerticalLayout layoutColumn3 = new VerticalLayout();
    HorizontalLayout layoutRow2 = new HorizontalLayout();
    H4 h4 = new H4();
    H4 h42 = new H4();
    H4 h43 = new H4();
    H4 h44 = new H4();
    HorizontalLayout layoutRow3 = new HorizontalLayout();
    H5 h5 = new H5();
    H5 h52 = new H5();
    TextField textField = new TextField();
    H5 h53 = new H5();
    HorizontalLayout layoutRow4 = new HorizontalLayout();
    H3 h3 = new H3();
    HorizontalLayout layoutRow5 = new HorizontalLayout();
    Button buttonPrimary4 = new Button();
    Long productId;

	public CartView() {        
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutRow2.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        h4.setText("Sr. No");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h4);
        h4.setWidth("max-content");
        h42.setText("Product Name");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h42);
        h42.getStyle().set("flex-grow", "1");
        h43.setText("Quantity");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h43);
        h43.getStyle().set("flex-grow", "1");
        h44.setText("Price");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, h44);
        h44.getStyle().set("flex-grow", "1");
        layoutRow3.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("30px");
        layoutRow3.setAlignItems(Alignment.CENTER);
        layoutRow3.setJustifyContentMode(JustifyContentMode.START);
        h5.setText("1");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.CENTER, h5);
        h5.setWidth("50px");
        h52.setText("Cordless Hammer Drill");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.CENTER, h52);
        h52.setWidth("375px");
        textField.setLabel("Choose");
        textField.setWidth("337px");
        textField.setHeight("88px");
        h53.setText("€259.99");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.CENTER, h53);
        h53.setWidth("max-content");
        layoutRow4.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.getStyle().set("flex-grow", "1");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.END);
        h3.setText("Total Price:  € 758");
        layoutRow4.setAlignSelf(FlexComponent.Alignment.CENTER, h3);
        h3.setWidth("300px");
        h3.setHeight("28px");
        layoutRow5.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setWidth("100%");
        layoutRow5.getStyle().set("flex-grow", "1");
        layoutRow5.setAlignItems(Alignment.CENTER);
        layoutRow5.setJustifyContentMode(JustifyContentMode.END);
        buttonPrimary4.setText("Proceed to Checkout Page");
        buttonPrimary4.setWidth("300px");
        buttonPrimary4.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(layoutRow2);
        layoutRow2.add(h4);
        layoutRow2.add(h42);
        layoutRow2.add(h43);
        layoutRow2.add(h44);
        layoutColumn3.add(layoutRow3);
        layoutRow3.add(h5);
        layoutRow3.add(h52);
        layoutRow3.add(textField);
        layoutRow3.add(h53);
        layoutColumn3.add(layoutRow4);
        layoutRow4.add(h3);
        layoutColumn3.add(layoutRow5);
        layoutRow5.add(buttonPrimary4);
        
        Grid<PurchaseItem> cart = new Grid<>();
        
    }

	@Override
	public void setParameter(BeforeEvent event, Long passedProductId) {
		this.productId = passedProductId;
		updatedUI(productId);
	}

	private void updatedUI(Long productId) {
		RestClient client = new RestClient();
		ResponseData data = client.requestHttp("GET", "http://localhost:8080/public/products/product/" + productId, null, null);
		JsonNode productNode = data.getNode();
		ObjectMapper mapper = new ObjectMapper();
		mapper.convertValue(productNode, Product.class);
		
	}
	
	
}
