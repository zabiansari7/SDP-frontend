package de.srh.toolify.frontend.views.products;

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
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.Product;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.utils.HelperUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        List<String> sortItems = HelperUtil.getAllCategories();
        sortItems.add(0, "All");
        sortBy.setItems(sortItems);
        sortBy.setValue("All");
        sortBy.addValueChangeListener(e -> {
        	String encodedParam;
        	ResponseData resp;
        	if (sortBy.getValue().equals("All")) {
        		resp = RestClient.requestHttp("GET", "http://localhost:8080/public/products/all", null, null);
        		populateProducts(resp, imageContainer);
			} else {
                encodedParam = URLEncoder.encode(sortBy.getValue(), StandardCharsets.UTF_8);
                resp = RestClient.requestHttp("GET", "http://localhost:8080/private/admin/products/product?categoryName=" + encodedParam, null, null);
				populateProducts(resp, imageContainer);
			}
        });

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        imageContainer.getElement().getStyle().set("grid-template-columns", "repeat(auto-fill, minmax(350px, 1fr))");
        
        container.add(sortBy);
        add(container, imageContainer);
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
