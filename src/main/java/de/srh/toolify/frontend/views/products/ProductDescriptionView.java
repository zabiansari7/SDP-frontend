package de.srh.toolify.frontend.views.products;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.avatar.AvatarGroup.AvatarGroupItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;

import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;

import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.ResponseData;
import de.srh.toolify.frontend.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "product", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ProductDescriptionView extends Composite<VerticalLayout> implements HasUrlParameter<Long>{

    private static final long serialVersionUID = -4195665265910762111L;
    
    HorizontalLayout layoutRow2 = new HorizontalLayout();
    AvatarGroup avatarGroup = new AvatarGroup();
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H1 title = new H1();
    H3 price = new H3();
    Div productImageDiv = new Div();
    Image image = new Image();
    Paragraph description = new Paragraph();
    HorizontalLayout layoutRow3 = new HorizontalLayout();
    IntegerField quantity = new IntegerField();
    Button addToCartButton = new Button();
    Grid<Pair<String, String>> stripedGrid = new Grid<>();
    Long producId;
    
	public ProductDescriptionView() {
        productImageDiv.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM);
        productImageDiv.setWidth("100%");
        image.setWidth("100%");
        image.setAlt("logo");
        productImageDiv.addClassName(Margin.MEDIUM);
        productImageDiv.add(image);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        avatarGroup.getStyle().set("flex-grow", "1");
        setAvatarGroupSampleData(avatarGroup);
        layoutColumn2.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        title.setWidth("max-content");
        price.setWidth("max-content");
        description.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("min-content");
        quantity.setStepButtonsVisible(true);
        quantity.setValue(1);
        quantity.setMin(1);
        quantity.setLabel("Quantity");
        quantity.setWidth("min-content");
        addToCartButton.setText("Add To Cart");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.END, addToCartButton);
        addToCartButton.setWidth("min-content");
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        stripedGrid.setWidth("100%");
        stripedGrid.getStyle().set("flex-grow", "0");
        //setGridSampleData(stripedGrid);
        getContent().add(layoutRow2);
        layoutRow2.add(productImageDiv);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(title);
        layoutColumn2.add(price);
        layoutColumn2.add(description);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(quantity);
        layoutRow3.add(addToCartButton);
        
    }

    private void setAvatarGroupSampleData(AvatarGroup avatarGroup) {
        avatarGroup.add(new AvatarGroupItem("A B"));
        avatarGroup.add(new AvatarGroupItem("C D"));
        avatarGroup.add(new AvatarGroupItem("E F"));
    }

	@Override
	public void setParameter(BeforeEvent event, Long passedProductId) {	
		this.producId = passedProductId;
		updateUI(producId);
	}

	
	private void updateUI(Long productId) {
		RestClient client = new RestClient();
		ResponseData data = client.requestHttp("GET", "http://localhost:8080/public/products/product/" + productId,	null, null);

		JsonNode productNode = data.getNode();
		String imageUrl = productNode.get("image").textValue();
		image.setSrc(imageUrl);
		title.setText(productNode.get("name").textValue());
		price.setText("€ " + productNode.get("price").toString());
		description.setText(productNode.get("description").textValue());
		
		client = new RestClient();
		data = client.requestHttp("GET", "http://localhost:8080/public/products/product/"+ productId +"/quantity", null, null);
		String maxQuantityNode = data.getNode().get("message").textValue();
		quantity.setMax(Integer.valueOf(maxQuantityNode));
		List<Pair<String, String>> keyValuePairs = convertPProductToList(productNode);
		stripedGrid.setItems(keyValuePairs);
		stripedGrid.addColumn(Pair::getKey).setHeader("Property");
		stripedGrid.addColumn(Pair::getValue).setHeader("Value");
		layoutColumn2.add(stripedGrid);

	}
	
	public List<Pair<String, String>> convertPProductToList(JsonNode product) {
        List<Pair<String, String>> keyValuePairs = new ArrayList<>();
        addKeyValuePairIfNotEmpty(keyValuePairs, "Manufacturer", product.get("manufacturer").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Voltage", product.get("voltage").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Dimensions", product.get("productDimensions").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Item Weight", product.get("itemWeight").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Body Material", product.get("bodyMaterial").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Item Model Number", product.get("itemModelNumber").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Design", product.get("design").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Colour", product.get("colour").textValue());
        addKeyValuePairIfNotEmpty(keyValuePairs, "Batteries Required ?", product.get("batteriesRequired").textValue());
        return keyValuePairs;
    }
	
	private void addKeyValuePairIfNotEmpty(List<Pair<String, String>> list, String key, String value) {
	    if (value != null && !value.isEmpty()) {
	        list.add(Pair.of(key, value));
	    }
	}
}
