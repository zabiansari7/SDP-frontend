package de.srh.toolify.frontend.views.register;

import java.io.IOException;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.data.User;
import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("Register")
@Route(value = "register", layout = MainLayout.class)
@Uses(Icon.class)
public class RegisterView extends Composite<VerticalLayout> {

    private static final long serialVersionUID = 4643655362138031508L;
    
    private Binder<User> binder = new Binder<>(User.class);
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H3 h3 = new H3();
    FormLayout formLayout2Col = new FormLayout();
    TextField firstname = new TextField();
    TextField lastname = new TextField();
    EmailField email = new EmailField();
    TextField mobile = new TextField();
    PasswordField password = new PasswordField();
    PasswordField repeatPassword = new PasswordField();
    TextField defaultStreetName = new TextField();
    TextField defaultStreetNumber = new TextField();
    TextField defaultPincode = new TextField();
    TextField defaultCity = new TextField();
    HorizontalLayout layoutRow = new HorizontalLayout();
    Button registerButton = new Button();
    Button alreadyMemberButton = new Button();
    
    private boolean valuesMatches;

	public RegisterView() {
		binder.bindInstanceFields(this);
		getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Register");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        
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
        email.setValueChangeMode(ValueChangeMode.EAGER);
        email.setRequiredIndicatorVisible(true);
        email.setRequired(true);
        email.setPattern("^[A-Za-z0-9._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$");
        email.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$");
        	email.setInvalid(!isValid);
        });
        
        
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
        
        password.setLabel("Password");
        password.setValueChangeMode(ValueChangeMode.EAGER);
        password.setWidth("min-content");
        password.setRequiredIndicatorVisible(true);
        password.setPattern("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$");
        password.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$");
        	password.setInvalid(!isValid);
        	if (isValid) {
            	password.setHelperText("");
			} else {
				password.setHelperText("Password must have at least 8 characters, one capital letter, one special character, and one digit");
			}
        });
//        binder.forField(password)
//        	.withValidator(this::isValidPassword, "Password must have at least 8 characters, one capital letter, one special character, and one digit.")
//        	.bind(User::getPassword, User::setPassword);
        
        repeatPassword.setLabel("Repeat Password");
        repeatPassword.setWidth("min-content");
        repeatPassword.setRequiredIndicatorVisible(true);
        repeatPassword.setRequired(true);
        
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
        });
        defaultStreetNumber.setRequired(true);
        defaultStreetNumber.setPattern("\\d{0,3}");
        defaultStreetNumber.setMaxLength(3);
        //defaultStreetNumber.setWidth("min-content");
        
       
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
        
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        registerButton.setText("Register");
        registerButton.addClickListener(c -> onRegister(binder));
        registerButton.setWidth("min-content");
        registerButton.addClassName("clickable-button");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        alreadyMemberButton.setText("Already a member? Log In here");
        alreadyMemberButton.setWidth("min-content");
        alreadyMemberButton.addClassName("clickable-button");
        alreadyMemberButton.addClickListener(c -> UI.getCurrent().navigate("/login"));
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(firstname);
        formLayout2Col.add(lastname);
        formLayout2Col.add(email);
        formLayout2Col.add(mobile);
        formLayout2Col.add(password);
        formLayout2Col.add(repeatPassword);
        formLayout2Col.add(defaultStreetName);
        formLayout2Col.add(defaultStreetNumber);
        formLayout2Col.add(defaultPincode);
        formLayout2Col.add(defaultCity);
        layoutColumn2.add(layoutRow);
        layoutRow.add(registerButton);
        layoutRow.add(alreadyMemberButton);
        
        User user = new User();
        binder.setBean(user);
    }
	

	private boolean isValidPassword(String value) {
	    // Password must have at least 8 characters, one capital letter, one special character, and one digit
	    String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
	    return value.matches(passwordRegex);
	}

	private void onRegister(
			Binder<User> binder) {
		if (binder.getFields().anyMatch(a -> a.isEmpty())) {
			Notification notification = Notification
					.show("Empty fields detected!!");
			notification.setDuration(5000);
			notification.setPosition(Position.BOTTOM_CENTER);
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			return;
		}
		
		
		
		if (!isValidPassword(binder.getBean().getPassword())) {
	        Notification notification = Notification
	                .show("Invalid password format. Please follow the password requirements.");
	        notification.setDuration(5000);
	        notification.setPosition(Position.BOTTOM_CENTER);
	        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
	        return;
	    }
		if (!this.isPasswordMatched(binder.getBean().getPassword(), binder.getBean().getRepeatPassword())) {
			Notification notification = Notification
			        .show("Password Mismatched !!!");
			notification.setDuration(5000);
			notification.setPosition(Position.BOTTOM_CENTER);
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		} else {
			RestClient client = new RestClient();
			ResponseData resp = client.requestHttp("POST", "http://localhost:8080/public/users/user", binder.getBean(), User.class);
			int responseCode = 0;
			try {
				responseCode = resp.getConnection().getResponseCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
            if (responseCode == 201) {
            	Notification notification = Notification.show("Registration successfully");
            	notification.setDuration(5000);
    			notification.setPosition(Position.BOTTOM_CENTER);
    			notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    			getUI().get().navigate("/login");
    			
            } else {
            	String errMessage = resp.getNode().get("message").textValue();
            	if (errMessage.contains("Duplicate entry")) {
            		errMessage = "Email already exist. Try with another email address";
				}
            	Notification notification = Notification.show(errMessage);
            	notification.setDuration(5000);
    			notification.setPosition(Position.BOTTOM_CENTER);
    			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
		}
	}
	
	
	
    
	private boolean isPasswordMatched(String password1, String password2) {
		return password1.equals(password2);
	}
    
}
