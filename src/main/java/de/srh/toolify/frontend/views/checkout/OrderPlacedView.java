package de.srh.toolify.frontend.views.checkout;

import java.util.ArrayList;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.views.MainLayout;
import de.srh.toolify.frontend.views.login.LoginView;
import jakarta.annotation.security.PermitAll;

@PageTitle("Thank You | Toolify")
@Route(value = "orderplaced", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class OrderPlacedView extends Composite<VerticalLayout> implements BeforeEnterObserver {

    private static final long serialVersionUID = 5782234946531038173L;

    public OrderPlacedView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        H2 h2 = new H2();
        H5 h5 = new H5();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("40px");
        layoutRow.setAlignItems(Alignment.START);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        h2.setText("Thank You! Your order has been placed successfully.");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, h2);
        h2.setWidth("max-content");
        h5.setText("You can view your order invoice in the Profile > Order History");
        h5.setWidth("max-content");
        buttonPrimary.setText("Back to Home");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addClickListener(e -> UI.getCurrent().navigate("home"));
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(h2);
        layoutColumn2.add(h5);
        layoutColumn2.add(buttonPrimary);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (VaadinSession.getCurrent().getAttribute("token") == null || VaadinSession.getCurrent().getAttribute("token").toString().isBlank() || VaadinSession.getCurrent().getAttribute("token").toString().isEmpty()) {
            event.forwardTo(LoginView.class);
            return;
        }
        VaadinSession.getCurrent().setAttribute("cartItems", new ArrayList<>());
    }
}