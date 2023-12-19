package de.srh.toolify.frontend.views.user;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.AddAddress;
import de.srh.toolify.frontend.data.Address;
import de.srh.toolify.frontend.data.EditUser;
import de.srh.toolify.frontend.data.PurchaseHistory;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.data.User;
import de.srh.toolify.frontend.data.UserForAddress;
import de.srh.toolify.frontend.utils.HelperUtil;
import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("Profile | Toolify")
@Route(value = "profile", layout = MainLayout.class)
@Uses(Icon.class)
public class UserProfileTabs extends Composite<VerticalLayout> {

    private static final long serialVersionUID = 1L;

    Binder<User> binder = new Binder<>(User.class);
    Binder<AddAddress> addressBinder = new Binder<>(AddAddress.class);
	VerticalLayout userDetailsMain = new VerticalLayout();
    FormLayout userDetailsFormLayout = new FormLayout();
    TextField firstname = new TextField();
    TextField lastname = new TextField();
    EmailField email = new EmailField();
    TextField mobile = new TextField();
    TextField defaultStreetName = new TextField();
    TextField defaultStreetNumber = new TextField();
    TextField defaultPincode = new TextField();
    TextField defaultCity = new TextField();
    HorizontalLayout userDetailsHorizontalLayout = new HorizontalLayout();
    Button userDetailsEditButton = new Button();
    Button userDetailsSaveButton = new Button();
    Button userDetailsCancelButton = new Button();
    String emailFromSession = HelperUtil.getEmailFromSession();
    
    private boolean valuesMatches;
    
	public UserProfileTabs() {
		binder.bindInstanceFields(this);
        HorizontalLayout layoutRow = new HorizontalLayout();
        TabSheet tabSheet = new TabSheet();     
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        tabSheet.setWidth("100%");
        setTabSheetSampleData(tabSheet, binder);
        getContent().add(layoutRow);
        layoutRow.add(tabSheet);
    }

    private void setTabSheetSampleData(TabSheet tabSheet, Binder<User> binder) {
        tabSheet.add("User Details", new Div(getUserDetailsLayout(binder))).addClassName("tabStyle");
        tabSheet.add("Order History", new Div(getUserOrdersLayout())).addClassName("tabStyle");
        tabSheet.add("Manage Address", new Div(getManageAddressesLayout())).addClassName("tabStyle");;
    }
    
    private VerticalLayout getUserDetailsLayout(Binder<User> binder) {    
    	
        
        getContent().setWidth("100%"); 
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        userDetailsMain.setWidth("100%");
        userDetailsMain.setMaxWidth("800px");
        userDetailsMain.setHeight("min-content");
        userDetailsFormLayout.setWidth("100%");
        firstname.setLabel("First Name");
        firstname.setValueChangeMode(ValueChangeMode.EAGER);
        firstname.setRequired(true);
        firstname.setPattern("^[a-zA-Z]*$");
        firstname.setMaxLength(30);
        firstname.setRequiredIndicatorVisible(true);
        firstname.setClearButtonVisible(true);
        firstname.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches(("\"^[a-zA-Z]*$\""));
        	firstname.setInvalid(!isValid);	
        });
        
        lastname.setLabel("Last Name");
        lastname.setValueChangeMode(ValueChangeMode.EAGER);
        lastname.setMaxLength(30);
        lastname.setRequiredIndicatorVisible(true);
        lastname.setRequired(true);
        lastname.setPattern("^[a-zA-Z]*$");
        lastname.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches(("^[a-zA-Z]*$"));
        	lastname.setInvalid(!isValid);
        });
        
        email.setLabel("Email");
        
        mobile.setLabel("Mobile");
        mobile.setRequiredIndicatorVisible(true);
        mobile.setMaxLength(15);
        mobile.setValueChangeMode(ValueChangeMode.EAGER);
        mobile.setPattern("^\\+\\d{0,15}$");
        mobile.addValueChangeListener(event -> {
            String value = event.getValue();
            boolean isValid = value.matches("^\\+\\d{0,15}$");
            mobile.setInvalid(!isValid);
            if (isValid) {
            	mobile.setHelperText("");
			} else {
				mobile.setHelperText("Mobile number should start with '+' and then only 15 numbers");
			}
            
        });
        
        defaultStreetName.setLabel("Street");
        defaultStreetName.setValueChangeMode(ValueChangeMode.EAGER);
        defaultStreetName.setRequiredIndicatorVisible(true);
        defaultStreetName.setPattern("^[a-zA-Z]*$");
        defaultStreetName.setRequired(true);
        defaultStreetName.setMaxLength(30);
        defaultStreetName.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches(("\"^[a-zA-Z]*$\""));
        	defaultStreetName.setInvalid(!isValid);
        });
        
        defaultStreetNumber.setLabel("Number");
        defaultStreetNumber.setRequiredIndicatorVisible(true);
        defaultStreetNumber.setValueChangeMode(ValueChangeMode.EAGER);
        defaultStreetNumber.addValueChangeListener(event -> {
            String newValue = event.getValue().replaceAll(",", "");
            defaultStreetNumber.setValue(newValue);
        defaultStreetNumber.setRequired(true);
        defaultStreetNumber.setPattern("\\d{0,3}");
        defaultStreetNumber.setMaxLength(3);
        //defaultStreetNumber.setWidth("min-content");
        });
        
        
        defaultPincode.setLabel("Pincode");
        defaultPincode.setRequired(true);
        defaultPincode.setRequiredIndicatorVisible(true);
        defaultPincode.setValueChangeMode(ValueChangeMode.EAGER);
        defaultPincode.addValueChangeListener(event -> {
            String newValue = event.getValue().replaceAll(",", "");
            defaultPincode.setValue(newValue);
        });
        defaultPincode.setPattern("\\d{0,5}");
        defaultPincode.setWidth("min-content");
        defaultPincode.setMaxLength(5);
      
        defaultCity.setLabel("City");
        defaultCity.setWidth("min-content");
        defaultCity.setValueChangeMode(ValueChangeMode.EAGER);
        defaultCity.setPattern("^[a-zA-Z]*$");;
        defaultCity.setMaxLength(30);
        defaultCity.setRequiredIndicatorVisible(true);
        defaultCity.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches(("^[a-zA-Z]*$"));
        	defaultCity.setInvalid(!isValid);
        });
        
        userDetailsHorizontalLayout.addClassName(Gap.MEDIUM);
        userDetailsHorizontalLayout.setWidth("100%");
        userDetailsHorizontalLayout.getStyle().set("flex-grow", "1");
        userDetailsEditButton.setText("Edit");
        userDetailsEditButton.setWidth("min-content");
        userDetailsEditButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        userDetailsSaveButton.setText("Save");
        userDetailsSaveButton.setWidth("min-content");
        userDetailsSaveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        userDetailsCancelButton.setText("Cancel");
        userDetailsCancelButton.setWidth("min-content");
        getContent().add(userDetailsMain);
        userDetailsMain.add(userDetailsFormLayout);
        userDetailsFormLayout.add(firstname);
        userDetailsFormLayout.add(lastname);
        userDetailsFormLayout.add(email);
        userDetailsFormLayout.add(mobile);
        userDetailsFormLayout.add(defaultStreetName);
        userDetailsFormLayout.add(defaultStreetNumber);
        userDetailsFormLayout.add(defaultPincode);
        userDetailsFormLayout.add(defaultCity);
        userDetailsMain.add(userDetailsHorizontalLayout);
        userDetailsHorizontalLayout.add(userDetailsEditButton);
        //userDetailsHorizontalLayout.add(userDetailsCancelButton);  
        
        User user = getUserByEmail();
        
        binder.setBean(user);
        binder.setReadOnly(true);

        userDetailsEditButton.addClickListener(e -> {
        	binder.setReadOnly(false);
        	email.setReadOnly(true);
        	binder.getFields().forEach(field -> field.setRequiredIndicatorVisible(true));
        	email.setRequiredIndicatorVisible(false);
        	userDetailsHorizontalLayout.removeAll();
        	userDetailsHorizontalLayout.add(userDetailsSaveButton, userDetailsCancelButton);
        });
        
        userDetailsCancelButton.addClickListener(e -> {
        	binder.setReadOnly(true);
        	userDetailsHorizontalLayout.removeAll();
        	userDetailsHorizontalLayout.add(userDetailsEditButton);
        	UI.getCurrent().getPage().reload();
        });
        
        userDetailsSaveButton.addClickListener(e -> {
        	if (binder.validate().isOk() == false) {
        		showNotification("Please correct your input", NotificationVariant.LUMO_ERROR);
    			return;
			}
        	
        	if (binder.getFields().anyMatch(a -> a.isEmpty())) {
    			showNotification("Empty fields detected !", NotificationVariant.LUMO_ERROR);
    			return;
    		}        	
        	String encodedEmail = null;
            try {
    			encodedEmail = URLEncoder.encode(emailFromSession, StandardCharsets.UTF_8.toString());
    		} catch (UnsupportedEncodingException ex) {
    			ex.printStackTrace();
    		}
            
        	EditUser editUser = prepareEditUser();
        	RestClient client = new RestClient();
        	client.requestHttp("PUT", "http://localhost:8080/private/user?email=" + encodedEmail, editUser, EditUser.class);
        	System.out.println("CHECK :: " + binder.getBean().getEmail());
        	userDetailsHorizontalLayout.removeAll();
        	userDetailsHorizontalLayout.add(userDetailsEditButton);
        	binder.setReadOnly(true);
        	showNotification("Your details has been updated successfully", NotificationVariant.LUMO_SUCCESS);
        	
        });
        
        return userDetailsMain;
	}
    
    private VerticalLayout getUserOrdersLayout() {    	
    	VerticalLayout main = new VerticalLayout();
    	
    	HorizontalLayout labelHorizontalLayout = headerOrderHistoryLayout();
    	main.add(labelHorizontalLayout);
    	
    	JsonNode orderListJsonNode = prepareOrderContent();
    	
    	List<PurchaseHistory> purchaseHistories = HelperUtil.sortByTimeDescending(orderListJsonNode);

    	int count = 0;    	
    	for (PurchaseHistory purchaseHistory : purchaseHistories) {
    		count++;
			HorizontalLayout itemHorizontalLayout = createHorizontalLayout();
			itemHorizontalLayout.add(createLabel(String.valueOf(count)), 
					createLabel(String.valueOf(purchaseHistory.getInvoice())), 
					createLabel(String.valueOf(purchaseHistory.getDate())), 
					createLabel(String.valueOf("€" + purchaseHistory.getTotalPrice())),
					createDownloadInvoiceButton(purchaseHistory.getInvoice()));
			main.add(itemHorizontalLayout);
		}
    	
        return main;
    }
    
    private VerticalLayout getManageAddressesLayout() {
    	VerticalLayout main = new VerticalLayout();
    	
    	HorizontalLayout labelHorizontalLayout = headerAddressLayout(main);
    	main.add(labelHorizontalLayout);
    	
    	JsonNode addressesNode = getAddressByEmail();
    	ObjectMapper mapper = new ObjectMapper();
    	int count = 0;
    	for (JsonNode addressNode : addressesNode) {
    		count++;
			Address address = mapper.convertValue(addressNode, Address.class);
			
			HorizontalLayout addressHorizontalLayout = createHorizontalLayout();
			addressHorizontalLayout.add(
					createLabel(String.valueOf(count)), 
					createLabel(address.getStreetName()), 
					createLabel(String.valueOf(address.getStreetNumber())), 
					createLabel(String.valueOf(address.getPostCode())),
					createLabel(address.getCityName()), 
					createDeleteAddressButton("Delete Address", ButtonVariant.LUMO_ERROR, address.getAddressID()));
			
			
			main.add(addressHorizontalLayout);
		}
    	
    	
    	
        return main;
	}
    
    private User getUserByEmail() {
    	RestClient client = new RestClient();
    	String encodedEmail = null;
        try {
			encodedEmail = URLEncoder.encode(emailFromSession, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        ResponseData data = client.requestHttp("GET", "http://localhost:8080/private/user?email=" + encodedEmail, null, null);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(data.getNode(), User.class);		
	}
    
    private EditUser prepareEditUser() {
    	EditUser editUser = new EditUser();
    	editUser.setFirstname(firstname.getValue());
    	editUser.setLastname(lastname.getValue());
    	editUser.setMobile(mobile.getValue());
    	editUser.setDefaultStreetName(defaultStreetName.getValue());
    	editUser.setDefaultStreetNumber(Long.valueOf(defaultStreetNumber.getValue()));
    	editUser.setDefaultPincode(Long.valueOf(defaultPincode.getValue()));
    	editUser.setDefaultCity(defaultCity.getValue());	
    	return editUser;
	}
    
    private H3 createHeader(String text) {
    	H3 header = new H3(text);
		header.setWidthFull();
		return header;
	}

	private H4 createLabel(String text) {
		H4 label = new H4(text);
		label.setWidth("20%");
		label.setWidthFull();
		addClassName(Padding.Left.MEDIUM);
		return label;
	}
	
	private HorizontalLayout createHorizontalLayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidthFull();
		horizontalLayout.setHeight("25px");
		return horizontalLayout;
	}
	
	private HorizontalLayout headerOrderHistoryLayout() {
		HorizontalLayout h = createHorizontalLayout();
		h.add(createHeader("SrNo."), createHeader("Invoice Number"),createHeader("Date"), createHeader("Total Price"), createHeader("Invoice PDF"));
		return h;
	}
	
	private HorizontalLayout headerAddressLayout(VerticalLayout mainLayout) {
		HorizontalLayout h = createHorizontalLayout();
		h.add(createHeader("SrNo."), createHeader("Street Name"),createHeader("Street Number"), createHeader("Postcode"), createHeader("City"), createAddAddressButton(mainLayout));
		return h;
	}
	
	private Button createDownloadInvoiceButton(int invoiceNo) {
		Button button = new Button("Downlaod PDF");
		button.getElement().setProperty("invoiceNo", invoiceNo);
		button.setWidth("20%");
		button.addClassName("clickable-button");
		return button;
	}
	
	private Button createDeleteAddressButton(String text, ButtonVariant variant, Long addressId) {
		Button button = new Button(text);
		//button.setWidth("20%");
		button.addClassName("clickable-button");
		button.addThemeVariants(variant);
		button.addClickListener(event -> {
			deleteAddressById(addressId);
			button.getParent().get().removeFromParent();
			showNotification("Address deleted successfully", NotificationVariant.LUMO_ERROR);
		});
		return button;
	}
	
	private Button createAddAddressButton(VerticalLayout mainLayout) {
		Button button = new Button("Add Address");
		//button.setWidth("20%");
		button.addClassName("clickable-button");
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		button.addClickListener(event -> {
			Dialog dialog = new Dialog();
			dialog.getElement().setAttribute("aria-label", "Create new address");
			mainLayout.add(dialog);
			dialog.add(prepareDialogComponent(dialog, mainLayout));
			dialog.open();
		});
		return button;
	}
	
	
	
	private void showNotification(String text, NotificationVariant variant) {
		Notification notification = Notification
				.show(text);
		notification.setDuration(5000);
		notification.setPosition(Position.TOP_CENTER);
		notification.addThemeVariants(variant);
	}
	
	private JsonNode prepareOrderContent() {
		String encodedEmail = null;
    	try {
			encodedEmail = URLEncoder.encode(emailFromSession, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	RestClient client = new RestClient();
    	ResponseData data = client.requestHttp("GET", "http://localhost:8080/private/purchases/history?email=" + encodedEmail, null, null);
    	
    	JsonNode purchaseOrderNode = data.getNode();
    	return purchaseOrderNode;
    	
		
	}
	
	private void showPdfInBrowser(byte[] pdfBytes) {
        StreamResource resource = new StreamResource("invoice.pdf", (InputStreamFactory) () -> new ByteArrayInputStream(pdfBytes));
        resource.setContentType("application/pdf");
        
        // now check link in the bookmark
    }
	
	private JsonNode getAddressByEmail() {
		String encodedEmail = null;
    	try {
			encodedEmail = URLEncoder.encode(emailFromSession, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	RestClient client = new RestClient();
    	ResponseData data = client.requestHttp("GET", "http://localhost:8080/private/addresses?email=" + encodedEmail, null, null);
    	
    	JsonNode addresses = data.getNode();
    	return addresses;
	}
	
	private void deleteAddressById(Long addressId) {
		RestClient client = new RestClient();
		client.requestHttp("DELETE", "http://localhost:8080/private/addresses/" + addressId, null, null);		
	}
	
	private VerticalLayout prepareDialogComponent(Dialog dialog, VerticalLayout main) {
		VerticalLayout dialogVerticalLayout = new VerticalLayout();
		HorizontalLayout layoutRow = new HorizontalLayout();
        TextField streetName = new TextField();
        TextField streetNumber = new TextField();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        TextField postcode = new TextField();
        TextField cityName = new TextField();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        Button saveButton = new Button();
        Button cancelButton = new Button();
        addressBinder.forField(streetName).bind(AddAddress::getStreetName, AddAddress::setStreetName);
        addressBinder.forField(streetNumber).bind(AddAddress::getStreetNumber, AddAddress::setStreetNumber);
        addressBinder.forField(postcode).bind(AddAddress::getPostCode, AddAddress::setPostCode);
        addressBinder.forField(cityName).bind(AddAddress::getCityName, AddAddress::setCityName);
        dialogVerticalLayout.setWidth("100%");
        dialogVerticalLayout.getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        //layoutRow.setWidth("1100px");
        layoutRow.setHeight("75px");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        streetName.setLabel("Street");
        streetName.setValueChangeMode(ValueChangeMode.EAGER);
        streetName.setRequiredIndicatorVisible(true);
        streetName.setPattern("^[a-zA-Z]*$");
        streetName.setRequired(true);
        streetName.setMaxLength(30);
        streetName.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches(("\"^[a-zA-Z]*$\""));
        	streetName.setInvalid(!isValid);
        });
        streetNumber.setLabel("Number");
        streetNumber.setRequiredIndicatorVisible(true);
        streetNumber.setValueChangeMode(ValueChangeMode.EAGER);
        streetNumber.addValueChangeListener(event -> {
            String newValue = event.getValue().replaceAll(",", "");
            streetNumber.setValue(newValue);
        streetNumber.setRequired(true);
        streetNumber.setPattern("\\d{0,3}");
        streetNumber.setMaxLength(3);
        //defaultStreetNumber.setWidth("min-content");
        });
        //layoutRow2.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("60px");
        layoutRow3.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidthFull();
        layoutRow3.setHeight("75px");
        layoutRow3.setAlignItems(Alignment.CENTER);
        layoutRow3.setJustifyContentMode(JustifyContentMode.CENTER);
        postcode.setLabel("Pincode");
        postcode.setRequired(true);
        postcode.setRequiredIndicatorVisible(true);
        postcode.setValueChangeMode(ValueChangeMode.EAGER);
        postcode.addValueChangeListener(event -> {
            String newValue = event.getValue().replaceAll(",", "");
            postcode.setValue(newValue);
        });
        postcode.setPattern("\\d{0,5}");
        postcode.setWidth("min-content");
        postcode.setMaxLength(5);
        cityName.setLabel("City");
        cityName.setWidth("min-content");
        cityName.setValueChangeMode(ValueChangeMode.EAGER);
        cityName.setPattern("^[a-zA-Z]*$");;
        cityName.setMaxLength(30);
        cityName.setRequiredIndicatorVisible(true);
        cityName.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches(("^[a-zA-Z]*$"));
        	cityName.setInvalid(!isValid);
        });
        layoutRow4.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setHeight("75px");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.CENTER);
        saveButton.setText("Save");
        layoutRow4.setAlignSelf(FlexComponent.Alignment.CENTER, saveButton);
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.setText("Cancel");
        layoutRow4.setAlignSelf(FlexComponent.Alignment.CENTER, cancelButton);
        cancelButton.setWidth("min-content");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialogVerticalLayout.add(layoutRow);
        layoutRow.add(streetName);
        layoutRow.add(streetNumber);
       // getContent().add(layoutRow2);
        //layoutRow2.add(layoutRow3);
        dialogVerticalLayout.add(layoutRow3);
        layoutRow3.add(postcode);
        layoutRow3.add(cityName);
        dialogVerticalLayout.add(layoutRow4);
        layoutRow4.add(saveButton);
        layoutRow4.add(cancelButton);
        
        cancelButton.addClickListener(e -> {
        	dialog.close();
        });
 
        saveButton.addClickListener(e -> {
        	if (addressBinder.validate().isOk() == false) {
        		showNotification("Please correct your input", NotificationVariant.LUMO_ERROR);
    			return;
			}
        	
        	if (addressBinder.getFields().anyMatch(a -> a.isEmpty())) {
    			showNotification("Empty fields detected !", NotificationVariant.LUMO_ERROR);
    			return;
    		}        	
        	addAddress(streetName.getValue(), Integer.valueOf(streetNumber.getValue()), Integer.valueOf(postcode.getValue()), cityName.getValue());
        	showNotification("Address saved successfully", NotificationVariant.LUMO_SUCCESS);
        	dialog.close();
        	main.removeAll();
        	main.getElement().executeJs("location.reload(true)");
        });
		return dialogVerticalLayout;
	}
	
	private JsonNode addAddress(String streetName, int streetNumber, int postcode, String city) {

    	AddAddress address = new AddAddress();
    	address.setStreetName(streetName);
    	address.setStreetNumber(String.valueOf(streetNumber));
    	address.setPostCode(String.valueOf(postcode));
    	address.setCityName(city);
    	
    	UserForAddress user = new UserForAddress();
    	user.setEmail(emailFromSession);
    	address.setUser(user);
    	
    	RestClient client = new RestClient();
    	ResponseData data = client.requestHttp("POST", "http://localhost:8080/private/addresses/address", address, AddAddress.class);
    	
    	JsonNode postMessage = data.getNode();
    	return postMessage;
	}
}