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
        firstname.setRequired(true);
        firstname.setRequiredIndicatorVisible(true);
        firstname.setClearButtonVisible(true);
        lastname.setLabel("Last Name");
        lastname.setRequiredIndicatorVisible(true);
        email.setLabel("Email");
        email.setRequiredIndicatorVisible(true);
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
        password.setWidth("min-content");
        password.setRequiredIndicatorVisible(true);
        binder.forField(password)
        	.withValidator(this::isValidPassword, "Password must have at least 8 characters, one capital letter, one special character, and one digit.")
        	.bind(User::getPassword, User::setPassword);
        
        repeatPassword.setLabel("Repeat Password");
        repeatPassword.setWidth("min-content");
        repeatPassword.setRequiredIndicatorVisible(true);
        defaultStreetName.setLabel("Street");
        defaultStreetName.setRequiredIndicatorVisible(true);
        defaultStreetNumber.setLabel("Number");
        defaultStreetNumber.setWidth("min-content");
        defaultStreetNumber.setRequiredIndicatorVisible(true);
        defaultPincode.setLabel("Pincode");
        defaultPincode.setWidth("min-content");
        defaultPincode.setRequired(true);
        defaultPincode.setRequiredIndicatorVisible(true);
        defaultCity.setLabel("City");
        defaultCity.setWidth("min-content");
        defaultCity.setRequiredIndicatorVisible(true);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        registerButton.setText("Register");
        registerButton.addClickListener(c -> onRegister(binder));
        registerButton.setWidth("min-content");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        alreadyMemberButton.setText("Already a member? Log In here");
        alreadyMemberButton.setWidth("min-content");
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
	
	private boolean isValidMobileNumber(String value) {
	    String mobileNumberRegex = "^\\\\+\\\\d{0,15}$";
	    return value.matches(mobileNumberRegex);
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
