package de.srh.toolify.frontend.views.products;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Product;
import de.srh.toolify.frontend.data.PurchaseItem;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.utils.HelperUtil;
import de.srh.toolify.frontend.views.cart.CartService;
import de.srh.toolify.frontend.views.cart.CartView;

public class ProductsCard extends ListItem{
	
	private static final long serialVersionUID = 1L;

	public ProductsCard(Long productId, String title, String url, BigDecimal price, String description) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM);
        div.setHeight("350px");
        div.setWidth("100%");

        Image image = new Image();
        image.setWidth("100%");
        image.addClassName("clickable-button");
        image.getElement().setProperty("productId", productId);
        image.setSrc(url);
        image.setAlt(title);
        image.addClickListener(e -> UI.getCurrent().navigate(ProductDescriptionView.class, productId));

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);
        
        Button headerButton = new Button(title);
        headerButton.getElement().setProperty("productId", productId);
        headerButton.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        headerButton.addClassName("clickable-button");
        headerButton.setWidth("100%");
        headerButton.addClickListener(e -> UI.getCurrent().navigate(ProductDescriptionView.class, productId));

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("€ " + price);

        Paragraph productDescription = new Paragraph(description);
        productDescription.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.getElement().setProperty("productId", productId);
        badge.addClassName("clickable-button");
        badge.setText("Add to Cart"); 
        badge.addClickListener(c -> {
            try {
                HelperUtil.getEmailFromSession();
                CartService.getInstance().addToCart(prepareCartItem(productId));
                HelperUtil.showNotification(String.format("Product '%s' added to cart", title), NotificationVariant.LUMO_CONTRAST, Position.TOP_CENTER);
            } catch (Exception e) {
                HelperUtil.showNotification("Please Login to add Products to Cart", NotificationVariant.LUMO_WARNING, Position.TOP_CENTER);
            }
        });
        add(div, headerButton, subtitle, productDescription, badge);
    }
	
	private PurchaseItem prepareCartItem(Long productId) {
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/public/products/product/" + productId, null, null);
		JsonNode productNode = data.getNode();
		ObjectMapper mapper = new ObjectMapper();
		Product product = mapper.convertValue(productNode, Product.class);
		return new PurchaseItem(product.getProductId(), product.getQuantity(), product.getPrice(), product.getName(), 1);
	}

}
