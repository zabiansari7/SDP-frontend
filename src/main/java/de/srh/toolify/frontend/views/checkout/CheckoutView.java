package de.srh.toolify.frontend.views.checkout;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Address;
import de.srh.toolify.frontend.data.CheckoutPurchaseItem;
import de.srh.toolify.frontend.data.PurchaseItem;
import de.srh.toolify.frontend.data.CheckoutRequest;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.data.User;
import de.srh.toolify.frontend.error.InternalErrorView;
import de.srh.toolify.frontend.utils.HelperUtil;
import de.srh.toolify.frontend.views.MainLayout;
import de.srh.toolify.frontend.views.login.LoginView;
import jakarta.annotation.security.PermitAll;

@PageTitle("Checkout")
@Route(value = "checkout", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class CheckoutView extends Composite<VerticalLayout> implements BeforeEnterObserver {
	
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
    Long addressId;
    BigDecimal totalPrice = BigDecimal.ZERO;
    
    @SuppressWarnings("unchecked")
    public CheckoutView() throws InterruptedException {
        
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

        String email;
        try {
            email = HelperUtil.getEmailFromSession();
        } catch (Exception e) {
            UI.getCurrent().getPage().setLocation("http://localhost:8081/login");
            return;
        }
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        String encodedEmail = URLEncoder.encode(Objects.requireNonNull(email), StandardCharsets.UTF_8);
        User user = null;
        ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/private/user?email=" + encodedEmail, null, null, token);
        try {
            if (data.getConnection().getResponseCode() != 200) {
                HelperUtil.showNotification("Error occurred while processing user information", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                user = mapper.convertValue(data.getNode(), User.class);
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred while processing user information", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }

        firstname.setValue(user.getFirstname());
        lastname.setValue(user.getLastname());
        defaultStreetName.setValue(user.getDefaultStreetName());
        defaultStreetNumber.setValue(String.valueOf(user.getDefaultStreetNumber()));
        defaultPincode.setValue(String.valueOf(user.getDefaultPincode()));
        defaultCity.setValue(user.getDefaultCity());
        
        JsonNode addressesNode = getAddresses(encodedEmail);
        
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
        
        this.setAddressId(null);
        
        selectAddress.setItems(addresses);
        selectAddress.setPlaceholder("Select your shipping address");
        selectAddress.setItemLabelGenerator(item -> String.format("%s - %d, %d - %s", item.getStreetName(), item.getStreetNumber(), item.getPostCode(), item.getCityName()));
        selectAddress.addValueChangeListener(event -> {
        	this.setAddressId(event.getValue().getAddressID());
            defaultStreetName.setValue(event.getValue().getStreetName());
            defaultStreetNumber.setValue(String.valueOf(event.getValue().getStreetNumber()));
            defaultPincode.setValue(String.valueOf(event.getValue().getPostCode()));
            defaultCity.setValue(event.getValue().getCityName());
        });
        
        layoutColumn3.add(orderLabel);
        headerLayout();
        listPurchaseItems();
        
        
        h3.setText("Total Price €" + this.getTotalPrice());
        h3.setWidth("max-content");       
        editCartButton.setText("Edit Cart");
        layoutColumn3.setAlignSelf(Alignment.END, editCartButton);
        editCartButton.setWidth("200px");
        editCartButton.addClickListener(event -> UI.getCurrent().navigate("cart"));
        payButton.setText("Pay");
        payButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        payButton.addClassName("payButton");
        layoutColumn3.setAlignSelf(Alignment.END, payButton);
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
        
        payButton.addClickListener(event -> {
        	CheckoutRequest purchaseRequest = new CheckoutRequest();
        	String e = HelperUtil.getEmailFromSession();
            purchaseRequest.setEmail(e);
            purchaseRequest.setAddressId(this.getAddressId());
            List<CheckoutPurchaseItem> checkoutPurchaseItems = new ArrayList<>();            
			List<PurchaseItem> purchaseItems =  (List<PurchaseItem>) VaadinSession.getCurrent().getAttribute("cartItems");
            for (PurchaseItem purchaseItem : purchaseItems) {
				CheckoutPurchaseItem checkoutPurchaseItem = new CheckoutPurchaseItem();
				checkoutPurchaseItem.setProductId(purchaseItem.getProductId());
				checkoutPurchaseItem.setQuantity(purchaseItem.getRequestedQuantity());
				checkoutPurchaseItems.add(checkoutPurchaseItem);
			}
            
            purchaseRequest.setPurchaseItems(checkoutPurchaseItems);
        	ResponseData responseData = RestClient.requestHttp("POST", "http://localhost:8080/private/purchase/product", purchaseRequest, CheckoutRequest.class, token);
            try {
                if (responseData.getConnection().getResponseCode() != 201) {
                    if (responseData.getNode().get("message").toString().contains("Quantity cannot go below 0")){
                        HelperUtil.showNotification("One of the Product is not available in the store", NotificationVariant.LUMO_WARNING, Notification.Position.TOP_CENTER);
                    } else {
                        HelperUtil.showNotification("Error occurred while purchasing the Product", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
                    }
                } else {
                    UI.getCurrent().navigate("orderplaced");
                }
            } catch (IOException | NullPointerException ex) {
                HelperUtil.showNotification("Error occurred while purchasing the Product", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
                throw new RuntimeException(ex);
            }




        	JsonNode responseNode = responseData.getNode();
        	int code = 0;
        	try {
				code = responseData.getConnection().getResponseCode();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	if (code == 201) {
        		UI.getCurrent().navigate("orderplaced");
			}
        });
    }
    
    private JsonNode getAddresses(String email) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/private/addresses?email=" + email, null, null, token);
        try {
            if (data.getConnection().getResponseCode() != 200) {
                HelperUtil.showNotification("Error occurred while processing user Address", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            } else {
                return data.getNode();
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred while processing user Address", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
		return null;
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
	
	@SuppressWarnings("unchecked")
	private List<HorizontalLayout> listPurchaseItems() {
		List<PurchaseItem> purchases = (List<PurchaseItem>) VaadinSession.getCurrent().getAttribute("cartItems");
		List<HorizontalLayout> horizontalLayouts = new ArrayList<>();
		
		for (PurchaseItem purchaseItem : purchases) {
			HorizontalLayout h = createHorizontalLayout();
			BigDecimal purchasePrice = purchaseItem.getPurchasePrice().multiply(BigDecimal.valueOf(purchaseItem.getRequestedQuantity()));
			this.setTotalPrice(this.getTotalPrice().add(purchasePrice));
			h.add(createLabel(purchaseItem.getProductName()), createLabel(String.valueOf(purchaseItem.getRequestedQuantity())), createLabel(String.valueOf(purchasePrice)));
			layoutColumn3.add(h);
		}
		return horizontalLayouts;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (Objects.equals(h3.getText(), "Total Price €0")) {
            event.rerouteTo(InternalErrorView.class);
        }
    }
}
