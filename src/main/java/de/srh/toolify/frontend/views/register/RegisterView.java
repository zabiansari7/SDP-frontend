package de.srh.toolify.frontend.views.register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

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
        email.setLabel("Email");
        mobile.setLabel("Mobile");
        password.setLabel("Password");
        password.setWidth("min-content");
        repeatPassword.setLabel("Repeat Password");
        repeatPassword.setWidth("min-content");
        defaultStreetName.setLabel("Street");
        defaultStreetNumber.setLabel("Number");
        defaultStreetNumber.setWidth("min-content");
        defaultPincode.setLabel("Pincode");
        defaultPincode.setWidth("min-content");
        defaultPincode.setRequired(true);
        defaultCity.setLabel("City");
        defaultCity.setWidth("min-content");
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
		if (!this.isPasswordMatched(binder.getBean().getPassword(), binder.getBean().getRepeatPassword())) {
			Notification notification = Notification
			        .show("Password Mismatched !!!");
			notification.setDuration(5000);
			notification.setPosition(Position.BOTTOM_CENTER);
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		} else {
			sendData(binder.getBean());
		}
	}
    
	private boolean isPasswordMatched(String password1, String password2) {
		return password1.equals(password2);
	}
	
	private void sendData(User user) {
		String endpointUrl = "http://localhost:8080/public/api/users/user";
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = null;
		try {
			jsonBody = objectMapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			URL url = new URL(endpointUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            JsonNode node = null;
            try {
            	try(InputStream inputStream = connection.getInputStream()){
            		try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
            			StringBuilder responseStringBuilder = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            responseStringBuilder.append(line);
                        }
                        // Print the server response body
                        System.out.println("Response Body: " + responseStringBuilder.toString());
                        node = objectMapper.readTree(responseStringBuilder.toString());
            		}
            	}
				
			} catch (IOException e) {
				try (InputStream errorStream = connection.getErrorStream()) {
			        if (errorStream != null) {
			            try (BufferedReader br = new BufferedReader(new InputStreamReader(errorStream))) {
			                StringBuilder responseStringBuilder = new StringBuilder();
			                String line;
			                while ((line = br.readLine()) != null) {
			                    responseStringBuilder.append(line);
			                }

			                // Print the server response body
			                System.out.println("Error Response Body: " + responseStringBuilder.toString());

			                // Convert the error response JSON to a JsonNode
			                node = objectMapper.readTree(responseStringBuilder.toString());

			                // Handle the error response as needed
			                //Notification.show("Error Response: " + node);
			            }
			        } else {
			            // No error stream available, handle accordingly
			            //Notification.show("No error stream available");
			        	System.out.println("ERROR NO STREAM AVAILABLE");
			        }
			    }
			}
            
            

            int responseCode = connection.getResponseCode();
            if (responseCode == 201) {
            	Notification notification = Notification.show("Registration successfully");
            	notification.setDuration(5000);
    			notification.setPosition(Position.BOTTOM_CENTER);
    			notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    			getUI().get().navigate("/login");
    			
            } else {
            	String errMessage = node.get("message").textValue();
            	if (errMessage.contains("Duplicate entry")) {
            		errMessage = "Email already exist. Try with another email address";
				}
            	Notification notification = Notification.show(errMessage);
            	notification.setDuration(5000);
    			notification.setPosition(Position.BOTTOM_CENTER);
    			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
		} catch (Exception e) {
            e.printStackTrace();
            //Notification.show("An error occurred: " + e.getMessage());
        }

	}
    
}
