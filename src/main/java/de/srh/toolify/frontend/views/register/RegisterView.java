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
import de.srh.toolify.frontend.utils.HelperUtil;
import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("Register")
@Route(value = "register", layout = MainLayout.class)
@Uses(Icon.class)
public class RegisterView extends Composite<VerticalLayout> {

    private static final long serialVersionUID = 4643655362138031508L;
    
    private final Binder<User> binder = new Binder<>(User.class);
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
        password.setRequiredIndicatorVisible(true);
        password.setValueChangeMode(ValueChangeMode.EAGER);
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
        
        repeatPassword.setLabel("Repeat Password");
        repeatPassword.setValueChangeMode(ValueChangeMode.EAGER);
        repeatPassword.setWidth("min-content");
        repeatPassword.setRequiredIndicatorVisible(true);

        defaultStreetName.setLabel("Street");
        defaultStreetName.setRequiredIndicatorVisible(true);
        defaultStreetName.setMaxLength(30);
        defaultStreetName.setValueChangeMode(ValueChangeMode.EAGER);
        defaultStreetName.setPattern("^[a-zA-Z]*$");
        defaultStreetName.addValueChangeListener(event -> {
        	String value = event.getValue();
        	boolean isValid = value.matches(("^[a-zA-Z]*$"));
        	defaultStreetName.setInvalid(!isValid);
        });
        
        defaultStreetNumber.setLabel("Number");
        defaultStreetNumber.setRequiredIndicatorVisible(true);
        defaultStreetNumber.setMaxLength(3);
        defaultStreetNumber.setValueChangeMode(ValueChangeMode.EAGER);
        defaultStreetNumber.setPattern("^\\d{0,3}$");
       
        defaultPincode.setLabel("Pincode");
        defaultPincode.setRequiredIndicatorVisible(true);
        defaultPincode.setMaxLength(6);
        defaultPincode.setValueChangeMode(ValueChangeMode.EAGER);
        defaultPincode.setPattern("^\\d{0,6}$");

        defaultCity.setLabel("City");
        defaultCity.setRequiredIndicatorVisible(true);
        defaultCity.setMaxLength(30);
        defaultCity.setValueChangeMode(ValueChangeMode.EAGER);
        defaultCity.setPattern("^[a-zA-Z]*$");
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

	private void onRegister(Binder<User> binder) {
        if (binder.validate().isOk() == false){
            HelperUtil.showNotification("Invalid Input !!", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
            return;
        }
		if (binder.getFields().anyMatch(a -> a.isEmpty())) {
            HelperUtil.showNotification("Empty fields detected!!", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
            return;
        }
		if (!isValidPassword(binder.getBean().getPassword())) {
            HelperUtil.showNotification("Invalid password format. Please follow the password requirements", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
	        return;
	    }
		if (!this.isPasswordMatched(binder.getBean().getPassword(), binder.getBean().getRepeatPassword())) {
            HelperUtil.showNotification("Password and Repeat Password Mismatched !!!", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
            return;
		}

        ResponseData resp = RestClient.requestHttp("POST", "http://localhost:8080/public/users/user", binder.getBean(), User.class);
        int responseCode;
        try {
            responseCode = resp.getConnection().getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (responseCode == 201) {
            HelperUtil.showNotification("Registration successfully", NotificationVariant.LUMO_SUCCESS, Position.TOP_CENTER);
            UI.getCurrent().navigate("/login");

        } else {
            String errMessage = resp.getNode().get("message").textValue();
            if (errMessage.contains("Duplicate entry")) {
                errMessage = "Email already exist. Try with another email address";
            }
            HelperUtil.showNotification(errMessage, NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
        }
	}
    
	private boolean isPasswordMatched(String password1, String password2) {
		return password1.equals(password2);
	}
    
}
