package de.srh.toolify.frontend.views.products;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Product;
import de.srh.toolify.frontend.data.ResponseData;

public class ProductsView extends Main implements HasComponents, HasStyle{
	
	private static final long serialVersionUID = 1L;

	private OrderedList imageContainer;
	
	ObjectMapper mapper = new ObjectMapper();

    public ProductsView(JsonNode products) {
        constructUI();
        if (products == null) {
			UI.getCurrent().navigate("error");
		} else {
	        mapper.registerModule(new JavaTimeModule());
	        for (JsonNode productNode : products) {
	        	Product product = mapper.convertValue(productNode, Product.class);
				imageContainer.add(new ProductsCard(product.getProductId(), product.getName(), product.getImage(), product.getPrice(), product.getDescription()));
			}
		}
    }

    private void constructUI() {
        addClassNames("products-view");
        addClassNames(Width.FULL, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Beautiful photos");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Royalty free photos and pictures, courtesy of Unsplash");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Filter");
        List<String> sortItems = getAllCategories();
        sortItems.add(0, "All");
        sortBy.setItems(sortItems);
        sortBy.setValue("All");
        sortBy.addValueChangeListener(e -> {
        	RestClient restClient = new RestClient();
        	String encodedParam = null;
        	ResponseData resp;
        	if (sortBy.getValue().equals("All")) {
        		resp = restClient.requestHttp("GET", "http://localhost:8080/public/products/all", null, null);
        		populateProducts(resp, imageContainer);
			} else {
				try {
					encodedParam = URLEncoder.encode(sortBy.getValue(), StandardCharsets.UTF_8.toString());
				} catch (UnsupportedEncodingException ex) {
					ex.printStackTrace();
				}
				resp = restClient.requestHttp("GET", "http://localhost:8080/private/admin/products/product?categoryName=" + encodedParam, null, null);
				populateProducts(resp, imageContainer);
			}
        });

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        imageContainer.getElement().getStyle().set("grid-template-columns", "repeat(auto-fill, minmax(350px, 1fr))");
        
        container.add(sortBy);
        add(container, imageContainer);
    }
    
    private List<String> getAllCategories() {
		RestClient client = new RestClient();
		ResponseData resp = client.requestHttp("GET", "http://localhost:8080/private/admin/categories/all", null, null);
		List<String> categories = new ArrayList<>();
		for (JsonNode categoryNode : resp.getNode()) {
			categories.add(categoryNode.get("categoryName").textValue());			
		}
		return categories;
	}
    
    private void populateProducts(ResponseData data, OrderedList imageContainer) {
    	JsonNode node = data.getNode();
		imageContainer.removeAll();
		for (JsonNode productNode : node) {
            Product product = mapper.convertValue(productNode, Product.class);
            imageContainer.add(new ProductsCard(product.getProductId(), product.getName(), product.getImage(), product.getPrice(), product.getDescription()));
        }
	}

}
