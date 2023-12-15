package de.srh.toolify.frontend.views.cart;

import java.util.ArrayList;
import java.util.List;

import de.srh.toolify.frontend.data.PurchaseItem;

public class CartService {
	private static final CartService INSTANCE = new CartService();
    private List<PurchaseItem> purchaseItems;

    private CartService() {
        purchaseItems = new ArrayList<>();
    }

    public static CartService getInstance() {
        return INSTANCE;
    }

    public List<PurchaseItem> getCartItems() {
        return new ArrayList<>(purchaseItems);
    }

    public void addToCart(PurchaseItem purchaseItem) {
        purchaseItems.add(purchaseItem);
    }
}
