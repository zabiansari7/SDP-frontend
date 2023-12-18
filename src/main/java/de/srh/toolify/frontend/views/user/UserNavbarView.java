package de.srh.toolify.frontend.views.user;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import de.srh.toolify.frontend.views.MainLayout;

@PageTitle("User Profile | Toolify")
@Route(value = "pr", layout = MainLayout.class)
@AnonymousAllowed
public class UserNavbarView extends Composite<HorizontalLayout> implements RouterLayout {
	
	private static final long serialVersionUID = -5757466439305897723L;
	
	SideNav nav = new SideNav();

	public UserNavbarView() {
		prepareSideNav();
		getContent().add(nav);
	}
	
	private void prepareSideNav() {
		SideNavItem dashboardLink = new SideNavItem("User Details", "user", VaadinIcon.USER_CARD.create());
		SideNavItem inboxLink = new SideNavItem("Order History", UserOrderHistoryView.class, VaadinIcon.SHOP.create());
		SideNavItem calendarLink = new SideNavItem("Manage Address", UserAddressView.class, VaadinIcon.ENVELOPE.create());
		nav.setCollapsible(false);
		
		//nav.setExpanded(true);
		nav.addItem(dashboardLink, inboxLink, calendarLink);

	}	
	
	
	

}
