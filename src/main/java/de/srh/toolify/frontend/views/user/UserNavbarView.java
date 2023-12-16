package de.srh.toolify.frontend.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("UserNavbarView")
@Route(value = "usernavbarview", layout = MainLayout.class)
@AnonymousAllowed
public class UserNavbarView extends VerticalLayout {

 public UserNavbarView() {
     createDrawer();
 }

 private void createDrawer() {
	    VerticalLayout layout = new VerticalLayout();
	    layout.addClassName("menu");
	    layout.add(createMenuItem("User Profile", "user"));
	    layout.add(createMenuItem("Order History", "usersorder"));
	    layout.add(createMenuItem("Addresses", "useraddress"));

	    add(layout);
	}


 private Button createMenuItem(String text, String route) {
	    Button button = new Button(text, event -> navigateTo(route));
	    button.addClassName("menu-button");
	    button.getElement().getThemeList().add("tertiary");

	    return button;
	}

 private void navigateTo(String route) {
     getUI().ifPresent(ui -> ui.navigate(route));
 }
}
