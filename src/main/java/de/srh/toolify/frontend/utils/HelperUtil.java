package de.srh.toolify.frontend.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.server.VaadinSession;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Category;
import de.srh.toolify.frontend.data.PurchaseHistory;
import de.srh.toolify.frontend.data.ResponseData;

public class HelperUtil {
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
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
		RestClient client = new RestClient();
		ResponseData resp = client.requestHttp("GET", "http://localhost:8080/private/admin/categories/all", null, null);
		List<String> categories = new ArrayList<>();
		for (JsonNode categoryNode : resp.getNode()) {
			categories.add(categoryNode.get("categoryName").textValue());
		}
		return categories;
	}

	public static List<Category> getAllCategoriesAsClass() {
		RestClient client = new RestClient();
		ResponseData resp = client.requestHttp("GET", "http://localhost:8080/private/admin/categories/all", null, null);
		ObjectMapper mapper = HelperUtil.getObjectMapper();
		List<Category> categories = new ArrayList<>();
		for (JsonNode categoryNode : resp.getNode()) {
			Category category = mapper.convertValue(categoryNode, Category.class);
			categories.add(category);
		}
		return categories;
	}

	public static PurchaseHistory getPurchaseByInvoice(int invoice) {
		RestClient client = new RestClient();
		ObjectMapper mapper = HelperUtil.getObjectMapper();
		ResponseData data =client.requestHttp("GET", "http://localhost:8080/private/purchase/history/" + invoice, null, null);
		JsonNode purchaseNode = data.getNode();
		PurchaseHistory purchaseHistory = mapper.convertValue(purchaseNode, PurchaseHistory.class);
		return purchaseHistory;
	}

}
