package de.srh.toolify.frontend.views;


import java.io.InputStream;
import java.util.ArrayList;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import de.srh.toolify.frontend.views.home.HomeView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = 5616033976064402712L;
    
    HorizontalLayout layoutRow = new HorizontalLayout();
    Button registerButton = new Button();
    Button loginButton = new Button();
    Button cartButton = new Button();
    Button profileButton = new Button();
    Button logoutButton = new Button();


    public MainLayout() {
        addToNavbar(createHeaderContent());
    }
    
    

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Div layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);
        
        
        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM);
        div.setHeight("100%");
        //div.setWidth("100%");

        Image image = new Image();
        image.setWidth("100%");
        image.addClassName("clickable-button");
        StreamResource resource = new StreamResource("toolify_logo.jpeg", this::getImageStream);
        image.setSrc(resource);
        image.setAlt("logo");
        image.addClickListener(e -> {
            UI.getCurrent().navigate(HomeView.class);
        });
        div.addClassName(Margin.MEDIUM);
        div.add(image);

        H1 appName = new H1("Toolify Shop");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        appName.addClickListener(click -> {
        	UI.getCurrent().navigate(HomeView.class);
        	System.out.println(UI.getCurrent().getElement().getChildCount());
        });
        
        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        registerButton.setText("Register");
        registerButton.setWidth("min-content");
        registerButton.setId("registerBtn");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClassName("clickable-button");
        registerButton.addClickListener(click -> UI.getCurrent().navigate("/register"));
        loginButton.setText("Log In");
        loginButton.setId("loginBtn");
        loginButton.setWidth("min-content");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClassName("clickable-button");
        loginButton.addClickListener(click -> UI.getCurrent().navigate("/login"));
        layoutRow.add(registerButton);
        layoutRow.add(loginButton);
        cartButton.setText("Cart");
        cartButton.setWidth("min-content");
        cartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cartButton.addClassName("clickable-button");
        cartButton.setId("cartBtn");
        cartButton.addClickListener(event -> UI.getCurrent().navigate("cart"));
        profileButton.setText("Profile");
        profileButton.setId("profileBtn");
        profileButton.setWidth("min-content");
        profileButton.addClassName("clickable-button");
        profileButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        profileButton.addClickListener(e -> UI.getCurrent().navigate("profile"));
        logoutButton.setText("Logout");
        logoutButton.setId("logoutBtn");
        logoutButton.setWidth("min-content");
        logoutButton.addClassName("clickable-button");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logoutButton.addClickListener(e -> {
        	VaadinSession.getCurrent().setAttribute("token", "");        	
        	VaadinSession.getCurrent().setAttribute("cartItems", new ArrayList<>());
        	VaadinSession.getCurrent().setAttribute("user", null);
			layoutRow.remove(cartButton, profileButton, logoutButton);
			layoutRow.add(loginButton, registerButton);
			UI.getCurrent().navigate("home");
        });
        layoutRow.setJustifyContentMode(JustifyContentMode.END);
        

        layout.add(div, layoutRow);

        //header.add(layout, nav); //Hide Default Menu Items
        header.add(layout);
        return header;
    }
    
    private InputStream getImageStream() {
        return getClass().getClassLoader().getResourceAsStream("images/toolify_logo.jpeg");
    }

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (VaadinSession.getCurrent().getAttribute("token") != null
				&& VaadinSession.getCurrent().getAttribute("token") != "") {
			layoutRow.remove(loginButton, registerButton);
			layoutRow.add(cartButton, profileButton, logoutButton);
		}

	}

}
