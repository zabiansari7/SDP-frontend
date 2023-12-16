package de.srh.toolify.frontend.views.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.server.VaadinSession;

import de.srh.toolify.frontend.data.PurchaseItem;

public class CartService {

	private static final String CART_ITEMS_ATTRIBUTE = "cartItems";

    private static final CartService INSTANCE = new CartService();

    private CartService() {}
    
    public static CartService getInstance() {
        return INSTANCE;
    }

    public List<PurchaseItem> getCartItems() {
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        List<PurchaseItem> cartItems = (List<PurchaseItem>) vaadinSession.getAttribute(CART_ITEMS_ATTRIBUTE);
        return cartItems != null ? new ArrayList<>(cartItems) : new ArrayList<>();
    }

    public void addToCart(PurchaseItem cartItem) {
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        List<PurchaseItem> cartItems = (List<PurchaseItem>) vaadinSession.getAttribute(CART_ITEMS_ATTRIBUTE);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        
//        Map<String, Integer> map = countOccurrences(cartItems);
//        for (PurchaseItem purchaseItem : cartItems) {
//			purchaseItem.setRequestedQuantity(map.get(purchaseItem.getProductName()));;
//		}
        cartItems.add(cartItem);
        vaadinSession.setAttribute(CART_ITEMS_ATTRIBUTE, cartItems);
    }
    
    public void removeFromCart(PurchaseItem cartItem) {
    	VaadinSession vaadinSession = VaadinSession.getCurrent();
        List<PurchaseItem> cartItems = (List<PurchaseItem>) vaadinSession.getAttribute(CART_ITEMS_ATTRIBUTE);
        cartItems.remove(cartItem);
        vaadinSession.setAttribute(CART_ITEMS_ATTRIBUTE, cartItems);
	}
    
    private static Map<String, Integer> countOccurrences(List<PurchaseItem> list) {
        Map<String, Integer> occurrencesMap = new HashMap<>();

        for (PurchaseItem value : list) {
            occurrencesMap.put(value.getProductName(), occurrencesMap.getOrDefault(value, 0) + 1);
        }

        return occurrencesMap;
    }
}
