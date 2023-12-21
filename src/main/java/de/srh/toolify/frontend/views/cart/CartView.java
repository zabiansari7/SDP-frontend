package de.srh.toolify.frontend.views.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.data.PurchaseItem;
import de.srh.toolify.frontend.utils.HelperUtil;
import de.srh.toolify.frontend.views.MainLayout;
import de.srh.toolify.frontend.views.login.LoginView;
import jakarta.annotation.security.PermitAll;

@PageTitle("Cart | Toolify")
@Route(value = "cart", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class CartView extends Composite<VerticalLayout> implements BeforeEnterObserver {

	private static final long serialVersionUID = -8245968777291604244L;

	VerticalLayout layoutColumn3 = new VerticalLayout();

	HorizontalLayout layoutRow4 = new HorizontalLayout();
	H3 totalPriceLabel = new H3();
	HorizontalLayout layoutRow5 = new HorizontalLayout();
	Button checkoutButton = new Button();
	Long productId;
	Integer requestedQuantity;

	HorizontalLayout headerLayout = new HorizontalLayout();
	
	BigDecimal totalPrice = BigDecimal.ZERO;

	public CartView() {
		getContent().setWidth("100%");
		getContent().getStyle().set("flex-grow", "1");
		layoutColumn3.setWidthFull();
		layoutColumn3.setWidth("100%");
		layoutColumn3.getStyle().set("flex-grow", "1");

		layoutRow4.setWidthFull();
		layoutColumn3.setFlexGrow(1.0, layoutRow4);
		layoutRow4.addClassName(Gap.MEDIUM);
		layoutRow4.setWidth("100%");
		layoutRow4.getStyle().set("flex-grow", "1");
		layoutRow4.setAlignItems(Alignment.CENTER);
		layoutRow4.setJustifyContentMode(JustifyContentMode.END);
		layoutRow4.setAlignSelf(FlexComponent.Alignment.CENTER, totalPriceLabel);
		totalPriceLabel.setWidth("300px");
		totalPriceLabel.setHeight("28px");
		layoutRow5.setWidthFull();
		layoutColumn3.setFlexGrow(1.0, layoutRow5);
		layoutRow5.addClassName(Gap.MEDIUM);
		layoutRow5.setWidth("100%");
		layoutRow5.getStyle().set("flex-grow", "1");
		layoutRow5.setAlignItems(Alignment.END);
		layoutRow5.setJustifyContentMode(JustifyContentMode.END);
		checkoutButton.setText("Proceed to Checkout Page");
		checkoutButton.setWidth("300px");
		checkoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		headerLayout.add(createHeader("SrNo."), createHeader("Product"), createHeader("Quantity"),
				createHeader("Price"));
		headerLayout.setWidthFull();
		headerLayout.addClassName(Gap.MEDIUM);

		layoutColumn3.setFlexGrow(1.0, headerLayout);

		layoutColumn3.add(layoutRow4);
		layoutRow4.add(totalPriceLabel);
		layoutColumn3.add(layoutRow5);
		layoutRow5.add(checkoutButton);

		getContent().add(headerLayout);
		
		List<PurchaseItem> carts = CartService.getInstance().getCartItems();

		
		int count = 1;
		for (PurchaseItem purchaseItem : carts) {
			HorizontalLayout itemLayout = new HorizontalLayout();
			itemLayout.setWidthFull();
			BigDecimal purchasePrice = purchaseItem.getPurchasePrice().multiply(BigDecimal.valueOf(purchaseItem.getRequestedQuantity()));
			totalPrice = this.getTotalPrice().add(purchasePrice);
			itemLayout.add(createLabel(String.valueOf(count)),
					createLabel(purchaseItem.getProductName()), createIntegerField(0,
							Integer.parseInt(purchaseItem.getQuantity()), purchaseItem.getRequestedQuantity(), purchaseItem),
					createLabel(String.valueOf("€" + purchasePrice)));
			count++;
			getContent().add(itemLayout);
		}
		checkoutButton.addClickListener(event -> {
			if (carts.isEmpty() || Objects.equals(totalPriceLabel.getElement().getText(), "€0.00")) {
				HelperUtil.showNotification("Cannot proceed to checkout if Cart is empty", NotificationVariant.LUMO_WARNING, Notification.Position.TOP_CENTER);
				return;
			}
			UI.getCurrent().navigate("checkout");
		});

		getContent().add(layoutColumn3);
		if (this.getTotalPrice() != null) {
			totalPriceLabel.setText("€" + this.getTotalPrice().toString());
		}
	}

	private H3 createHeader(String text) {
		H3 header = new H3(text);
		header.setWidthFull();
		return header;
	}

	private H4 createLabel(String text) {
		H4 label = new H4(text);
		label.setWidthFull();
		return label;
	}

	private IntegerField createIntegerField(int min, int max, int value, PurchaseItem purchaseItem) {
		IntegerField integerField = new IntegerField();
		integerField.setWidthFull();
		integerField.setStepButtonsVisible(true);
		integerField.setMin(min);
		integerField.setMax(max);
		integerField.setValue(value);
		integerField.addValueChangeListener(e -> {
			
			BigDecimal purchasePrice = purchaseItem.getPurchasePrice().multiply(BigDecimal.valueOf(e.getValue()));
			integerField.getElement().getParent().getChild(3).setText(String.valueOf("€" + purchasePrice));
			BigDecimal newTotalPrice = BigDecimal.ZERO;
			
			if (e.getValue().equals(0)) {
				integerField.getParent().ifPresent(parent -> parent.getElement().removeFromParent());
				CartService.getInstance().removeFromCart(purchaseItem);
				BigDecimal priceToSubtract = purchaseItem.getPurchasePrice();
				newTotalPrice = this.getTotalPrice().subtract(priceToSubtract);
				this.setTotalPrice(newTotalPrice);
				totalPriceLabel.setText("€" + this.getTotalPrice().toString());
			} else if (e.getOldValue() < e.getValue()) {
				int diff = e.getValue() - e.getOldValue();
				purchaseItem.setRequestedQuantity(e.getValue());
				BigDecimal priceToAdd = purchaseItem.getPurchasePrice().multiply(BigDecimal.valueOf(diff));
				newTotalPrice = this.getTotalPrice().add(priceToAdd);
				this.setTotalPrice(newTotalPrice);
			}			
			else {
				int diff = e.getOldValue() - e.getValue();	
				purchaseItem.setRequestedQuantity(e.getValue());
				BigDecimal priceToSubtract = purchaseItem.getPurchasePrice().multiply(BigDecimal.valueOf(diff));
				newTotalPrice = this.getTotalPrice().subtract(priceToSubtract);
				this.setTotalPrice(newTotalPrice);
			}
			totalPriceLabel.setText("€" + newTotalPrice.toString());
			
		});
		
		return integerField;
	}
	
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		try {
			HelperUtil.getEmailFromSession();
		} catch (Exception e) {
			event.forwardTo(LoginView.class);
			return;
		}
	}
}
