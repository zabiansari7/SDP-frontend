package de.srh.toolify.frontend.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("AdminNavbarView")
@Route(value = "adminnavbarview", layout = MainLayout.class)
@AnonymousAllowed
public class AdminNavbarView extends VerticalLayout {

 public AdminNavbarView() {
     createDrawer();
 }

 private void createDrawer() {
	    VerticalLayout layout = new VerticalLayout();
	    layout.addClassName("menu");
	    layout.add(createMenuItem("Admin Profile", "adminprofile"));
	    layout.add(createMenuItem("User Order History", "adminorderhistory"));
	    layout.add(createMenuItem("Manage Products", "adminproducts"));

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
