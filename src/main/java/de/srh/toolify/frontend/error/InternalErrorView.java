package de.srh.toolify.frontend.error;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import de.srh.toolify.frontend.views.MainLayout;
import de.srh.toolify.frontend.views.home.HomeView;

@PageTitle("Internal Error | Toolify")
@Route(value = "internalerror", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class InternalErrorView extends Composite<VerticalLayout> {

    public InternalErrorView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        h2.setText("Sorry! Something went wrong :(");
        h2.setWidth("max-content");
        buttonPrimary.setText("Back to Home");
        buttonPrimary.addClickListener(e -> UI.getCurrent().navigate(HomeView.class));
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h2);
        layoutColumn2.add(buttonPrimary);
    }
}
