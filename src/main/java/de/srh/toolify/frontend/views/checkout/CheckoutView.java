package de.srh.toolify.frontend.views.checkout;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Address;
import de.srh.toolify.frontend.data.PurchaseItem;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.data.User;
import de.srh.toolify.frontend.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Checkout")
@Route(value = "checkout", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class CheckoutView extends Composite<VerticalLayout> {
	
	private static final long serialVersionUID = 8130844063643921264L;

	HorizontalLayout layoutRow = new HorizontalLayout();

    HorizontalLayout layoutRow2 = new HorizontalLayout();
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H2 h2 = new H2();
    H4 h4 = new H4();
    HorizontalLayout layoutRow3 = new HorizontalLayout();
    TextField firstname = new TextField();
    TextField lastname = new TextField();
    H4 h42 = new H4();
    HorizontalLayout layoutRow4 = new HorizontalLayout();
    TextField defaultStreetName = new TextField();
    TextField defaultStreetNumber = new TextField();
    HorizontalLayout layoutRow5 = new HorizontalLayout();
    TextField defaultPincode = new TextField();
    TextField defaultCity = new TextField();
    ComboBox<Address> selectAddress = new ComboBox<Address>();
    VerticalLayout layoutColumn3 = new VerticalLayout();
    H2 orderLabel = new H2();

    HorizontalLayout layoutRow8 = new HorizontalLayout();
    H3 h3 = new H3();
    HorizontalLayout editAndPayHorizontalLayout = new HorizontalLayout();
    Button editCartButton = new Button();
    Button payButton = new Button();

    public CheckoutView() {
        
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        layoutRow.setAlignItems(Alignment.START);
        layoutRow.setJustifyContentMode(JustifyContentMode.END);

        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("634px");
        layoutColumn2.setHeight("min-content");
        h2.setText("Checkout");
        h2.setWidth("max-content");
        h4.setText("Personal Details");
        h4.setWidth("max-content");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("min-content");
        firstname.setLabel("First Name");
        firstname.getStyle().set("flex-grow", "1");
        firstname.setHeight("75px");
        lastname.setLabel("Last Name");
        lastname.getStyle().set("flex-grow", "1");
        lastname.setHeight("75px");
        h42.setText("Shipping Address");
        h42.setWidth("max-content");
        layoutRow4.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.setHeight("min-content");
        defaultStreetName.setLabel("Street Name");
        defaultStreetName.setWidth("400px");
        defaultStreetName.setHeight("50px");
        defaultStreetNumber.setLabel("Street Number");
        defaultStreetNumber.setWidth("400px");
        defaultStreetNumber.setHeight("50px");
        layoutRow5.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setWidth("100%");
        layoutRow5.setHeight("min-content");
        defaultPincode.setLabel("Postcode");
        defaultPincode.setWidth("400px");
        defaultPincode.setHeight("50px");
        defaultCity.setLabel("City");
        defaultCity.setWidth("400px");
        defaultCity.setHeight("50px");
        selectAddress.setLabel("Select Address");
        selectAddress.setWidth("100%");
        layoutColumn3.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("634px");
        layoutColumn3.setHeight("100%");
        orderLabel.setText("Order");
        orderLabel.setWidth("max-content");
        layoutRow8.setWidthFull();
        layoutColumn3.setFlexGrow(1.0, layoutRow8);
        layoutRow8.addClassName(Gap.MEDIUM);
        layoutRow8.setWidth("100%");
        layoutRow8.setHeight("min-content");
        layoutRow8.setAlignItems(Alignment.CENTER);
        layoutRow8.setJustifyContentMode(JustifyContentMode.END);
        layoutColumn3.setFlexGrow(1.0, editAndPayHorizontalLayout);
        editAndPayHorizontalLayout.addClassName(Gap.MEDIUM);
        editAndPayHorizontalLayout.setWidth("100%");
        editAndPayHorizontalLayout.setHeight("min-content");
        editAndPayHorizontalLayout.setAlignItems(Alignment.CENTER);
        editAndPayHorizontalLayout.setJustifyContentMode(JustifyContentMode.END);
        
        firstname.setReadOnly(true);
        lastname.setReadOnly(true);
        defaultStreetName.setReadOnly(true);
        defaultStreetNumber.setReadOnly(true);
        defaultPincode.setReadOnly(true);
        defaultCity.setReadOnly(true);
        
        RestClient client = new RestClient();
        String email = getEmailFromSession();
        String encodedEmail = null;
        try {
			encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        ResponseData data = client.requestHttp("GET", "http://localhost:8080/private/user?email=" + encodedEmail, null, null);
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(data.getNode(), User.class);
        
        firstname.setValue(user.getFirstname());
        lastname.setValue(user.getLastname());
        defaultStreetName.setValue(user.getDefaultStreetName());
        defaultStreetNumber.setValue(String.valueOf(user.getDefaultStreetNumber()));
        defaultPincode.setValue(String.valueOf(user.getDefaultPincode()));
        defaultCity.setValue(user.getDefaultCity());
        
        JsonNode addressesNode = getAddresses(client, encodedEmail);
        System.out.println(addressesNode);
        
        ObjectMapper addressObjectMapper = new ObjectMapper();
        List<Address> addresses = new ArrayList<>();
        Address defaultAddress = new Address();
        defaultAddress.setStreetName(user.getDefaultStreetName());
        defaultAddress.setStreetNumber(Integer.valueOf(user.getDefaultStreetNumber().toString()));
        defaultAddress.setPostCode(Integer.valueOf(user.getDefaultPincode().toString()));
        defaultAddress.setCityName(user.getDefaultCity());
        
        addresses.add(0, defaultAddress);
        for (JsonNode addressNode : addressesNode) {
        	Address address = addressObjectMapper.convertValue(addressNode, Address.class);
        	addresses.add(address);
		}
        
        
        selectAddress.setItems(addresses);
        selectAddress.setPlaceholder("Select your shipping address");
        selectAddress.setItemLabelGenerator(item -> String.format("%s - %d, %d - %s", item.getStreetName(), item.getStreetNumber(), item.getPostCode(), item.getCityName()));
        selectAddress.addValueChangeListener(event -> {
            defaultStreetName.setValue(event.getValue().getStreetName());
            defaultStreetNumber.setValue(String.valueOf(event.getValue().getStreetNumber()));
            defaultPincode.setValue(String.valueOf(event.getValue().getPostCode()));
            defaultCity.setValue(event.getValue().getCityName());
        });
        
        layoutColumn3.add(orderLabel);
        headerLayout();
        listPurchaseItems();
        
        
        h3.setText("Total Price €259.99");
        h3.setWidth("max-content");       
        editCartButton.setText("Edit Cart");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.END, editCartButton);
        editCartButton.setWidth("200px");
        editCartButton.addClickListener(event -> UI.getCurrent().navigate("cart"));
        payButton.setText("Pay");
        payButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        payButton.addClassName("payButton");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.END, payButton);
        payButton.setWidth("200px");
        getContent().add(layoutRow);
        
        

        getContent().add(layoutRow2);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(h2);
        layoutColumn2.add(h4);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(firstname);
        layoutRow3.add(lastname);
        layoutColumn2.add(h42);
        layoutColumn2.add(layoutRow4);
        layoutRow4.add(defaultStreetName);
        layoutRow4.add(defaultStreetNumber);
        layoutColumn2.add(layoutRow5);
        layoutRow5.add(defaultPincode);
        layoutRow5.add(defaultCity);
        layoutColumn2.add(selectAddress);
        layoutRow2.add(layoutColumn3);
        
        layoutColumn3.add(layoutRow8);
        layoutRow8.add(h3);
        
        editAndPayHorizontalLayout.add(editCartButton, payButton);
        layoutColumn3.add(editAndPayHorizontalLayout);
    }
    
    private JsonNode getAddresses(RestClient client, String email) {
		ResponseData data = client.requestHttp("GET", "http://localhost:8080/private/addresses?email=" + email, null, null);
		return data.getNode();
    }

    private String getEmailFromSession() {
    	JsonNode userNode = (JsonNode) VaadinSession.getCurrent().getAttribute("user");
    	return userNode.get("email").textValue();
	}
    
    private H3 createHeader(String text) {
    	H3 header = new H3(text);
		header.setWidthFull();
		return header;
	}

	private H5 createLabel(String text) {
		H5 label = new H5(text);
		label.setWidthFull();
		return label;
	}
	
	private HorizontalLayout createHorizontalLayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidthFull();
		horizontalLayout.setHeight("25px");
		return horizontalLayout;
	}
	
	private HorizontalLayout headerLayout() {
		HorizontalLayout h = createHorizontalLayout();
		h.add(createHeader("Product"), createHeader("Quantity"),createHeader("Price"));
		layoutColumn3.add(h);
		return h;
	}
	
	private List<HorizontalLayout> listPurchaseItems() {
		List<PurchaseItem> purchases = (List<PurchaseItem>) VaadinSession.getCurrent().getAttribute("cartItems");
		List<HorizontalLayout> horizontalLayouts = new ArrayList<>();
		
		for (PurchaseItem purchaseItem : purchases) {
			HorizontalLayout h = createHorizontalLayout();
			BigDecimal purchasePrice = purchaseItem.getPurchasePrice().multiply(BigDecimal.valueOf(purchaseItem.getRequestedQuantity()));
			h.add(createLabel(purchaseItem.getProductName()), createLabel(String.valueOf(purchaseItem.getRequestedQuantity())), createLabel(String.valueOf(purchasePrice)));
			layoutColumn3.add(h);
		}
		return horizontalLayouts;
	}
}
