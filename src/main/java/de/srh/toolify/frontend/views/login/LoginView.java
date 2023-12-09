package de.srh.toolify.frontend.views.login;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.data.LoginRequest;
import de.srh.toolify.frontend.data.User;
import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
@Uses(Icon.class)
public class LoginView extends Composite<VerticalLayout> {
	private Binder<User> binder = new Binder<>(User.class);
	VerticalLayout layoutColumn2 = new VerticalLayout();
    H3 h3 = new H3();
    VerticalLayout layoutColumn3 = new VerticalLayout();
    EmailField email = new EmailField();
    PasswordField password = new PasswordField();
    HorizontalLayout layoutRow = new HorizontalLayout();
    Button loginButton = new Button();
    Button newUserRegisterButton = new Button();
    
    public LoginView() {
    	binder.bindInstanceFields(this);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Login");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.START, h3);
        h3.setWidth("100%");
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        email.setLabel("Email");
        email.setWidth("359px");
        password.setLabel("Password");
        password.setWidth("359px");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("min-content");
        layoutRow.getStyle().set("flex-grow", "1");
        loginButton.setText("Log In");
        loginButton.setWidth("min-content");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClickListener(c -> onLogInClick(binder.getBean()));
        newUserRegisterButton.setText("New User? Register here");
        newUserRegisterButton.setWidth("min-content");
        newUserRegisterButton.addClickListener(c -> UI.getCurrent().navigate("/register"));
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(email);
        layoutColumn3.add(password);
        layoutColumn3.add(layoutRow);
        layoutRow.add(loginButton);
        layoutRow.add(newUserRegisterButton);
        
        User user = new User();
        binder.setBean(user);
    }
    
    private void onLogInClick(User user) {
    	if (binder.getFields().anyMatch(a -> a.isEmpty())) {
			Notification notification = Notification
					.show("Empty fields detected!!");
			notification.setDuration(5000);
			notification.setPosition(Position.BOTTOM_CENTER);
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			return;
		}
		authenticate(user.getEmail(), user.getPassword());
		
	}
    
    private void authenticate(String email, String password) {
        // Make a request to Spring Boot backend for authentication
        String apiUrl = "http://localhost:8080/public/api/login/user";
        RestTemplate restTemplate = new RestTemplate();
        
     // Set Content-Type header to JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        System.out.println("LOGINNNNNN :: " + email +"___"+ password);
        LoginRequest loginRequest = new LoginRequest(email, password);
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);
        ResponseEntity<String> response = null;
        try {
        	response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
            
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show("Authenitcation Failed");
		}
       

        // Check the response and handle accordingly
        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            // Authentication successful, store the token in the Vaadin session
            String token = response.getBody();
            VaadinSession.getCurrent().setAttribute("authToken", token);
            
            Notification notification = Notification.show("Login successful");
        	notification.setDuration(5000);
			notification.setPosition(Position.BOTTOM_CENTER);
			notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			
            // Navigate to the secured part of your Vaadin application
            UI.getCurrent().navigate("hello");
        } else {
            // Authentication failed, show an error message
            Notification.show("Authentication failed", 3000, Position.TOP_CENTER);
        }
    }
}
