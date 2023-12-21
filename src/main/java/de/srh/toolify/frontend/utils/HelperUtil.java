package de.srh.toolify.frontend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.VaadinSession;
import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Category;
import de.srh.toolify.frontend.data.PurchaseHistory;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.data.User;
import de.srh.toolify.frontend.views.login.LoginView;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HelperUtil {
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	public static void showNotification(String text, NotificationVariant variant, Notification.Position position) {
		Notification notification = Notification
				.show(text);
		notification.setDuration(5000);
		notification.setPosition(position);
		notification.addThemeVariants(variant);
	}
	
	public static String getEmailFromSession() {
    	JsonNode userNode = (JsonNode) VaadinSession.getCurrent().getAttribute("user");
    	return userNode.get("email").textValue();
	}
	
	public static List<PurchaseHistory> sortByTimeDescending(JsonNode purchaseHistoriesNode) {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		purchaseHistoriesNode.forEach(purchaseHistoryNode -> {
			PurchaseHistory purchaseHistory = mapper.convertValue(purchaseHistoryNode, PurchaseHistory.class);
			purchaseHistories.add(purchaseHistory);
		});
		
        return purchaseHistories.stream()
                .sorted(Comparator.comparing(PurchaseHistory::getDate).reversed())
                .collect(Collectors.toList());
    }
	
	public static List<String> getAllCategories() {
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/public/categories/all", null, null);
		try {
			if (data.getConnection().getResponseCode() != 200) {
				HelperUtil.showNotification("Error occurred while processing categories", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			} else {
				List<String> categories = new ArrayList<>();
				for (JsonNode categoryNode : data.getNode()) {
					categories.add(categoryNode.get("categoryName").textValue());
				}
				return categories;
			}
		} catch (IOException e) {
			HelperUtil.showNotification("Error occurred while processing categories", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			throw new RuntimeException(e);
		}
		return null;
	}

	public static ResponseData getAllProducts() {
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/public/products/all", null, null);
		try {
			if (data.getConnection().getResponseCode() != 200) {
				HelperUtil.showNotification("Error occurred while processing products", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			} else {
				return data;
			}
		} catch (IOException e) {
			HelperUtil.showNotification("Error occurred while processing products", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			throw new RuntimeException(e);
		}
		return null;
	}

	public static List<Category> getAllCategoriesAsClass() {
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/public/categories/all", null, null);
		try {
			if (data.getConnection().getResponseCode() != 200) {
				HelperUtil.showNotification("Error occurred while processing categories", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			} else {
				ObjectMapper mapper = HelperUtil.getObjectMapper();
				List<Category> categories = new ArrayList<>();
				for (JsonNode categoryNode : data.getNode()) {
					Category category = mapper.convertValue(categoryNode, Category.class);
					categories.add(category);
				}
				return categories;
			}
		} catch (IOException e) {
			HelperUtil.showNotification("Error occurred while processing categories", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			throw new RuntimeException(e);
		}
		return null;
	}

	public static PurchaseHistory getPurchaseByInvoice(int invoice) {
		ObjectMapper mapper = HelperUtil.getObjectMapper();
		String token = (String) VaadinSession.getCurrent().getAttribute("token");
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/private/purchase/history/" + invoice, null, null, token);
		try {
			if (data.getConnection().getResponseCode() != 200) {
				HelperUtil.showNotification("Error occurred while downloading invoice", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			} else {
				JsonNode purchaseNode = data.getNode();
				PurchaseHistory purchaseHistory = mapper.convertValue(purchaseNode, PurchaseHistory.class);
				return purchaseHistory;
			}
		} catch (IOException e) {
			HelperUtil.showNotification("Error occurred while downloading invoice", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			throw new RuntimeException(e);
		}
		return null;
	}

	public static User getUserByEmail() {
		String email;
		try {
			email = HelperUtil.getEmailFromSession();
		} catch (Exception e) {
			UI.getCurrent().navigate(LoginView.class);
			return new User();
		}
		String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
		String token = (String) VaadinSession.getCurrent().getAttribute("token");
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/private/user?email=" + encodedEmail, null, null, token);
		try {
			if (data.getConnection().getResponseCode() != 200) {
				HelperUtil.showNotification("Error occurred while processing user information", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			} else {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.convertValue(data.getNode(), User.class);
			}
		} catch (IOException e) {
			HelperUtil.showNotification("Error occurred while processing user information", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
			throw new RuntimeException(e);
		}
		return null;
	}
}
