package de.srh.toolify.frontend.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.server.VaadinSession;

import de.srh.toolify.frontend.data.PurchaseHistory;

public class HelperUtil {
	
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

}
