package de.srh.toolify.frontend.views.user;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import de.srh.toolify.frontend.views.MainLayout;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Add Product Dialog")
@Route(value = "add-product-dialog", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ProductDialogView extends Composite<VerticalLayout> {

    public ProductDialogView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        TextField textField4 = new TextField();
        TextField textField5 = new TextField();
        TextField textField6 = new TextField();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        TextField textField7 = new TextField();
        TextField textField8 = new TextField();
        TextField textField9 = new TextField();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        TextField textField10 = new TextField();
        TextField textField11 = new TextField();
        TextField textField12 = new TextField();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        TextField textField13 = new TextField();
        TextField textField14 = new TextField();
        ComboBox comboBox = new ComboBox();
        HorizontalLayout layoutRow6 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonPrimary2 = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("1100px");
        layoutRow.setHeight("75px");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        textField.setLabel("Product Id");
        textField.setWidth("180px");
        textField2.setLabel("Name");
        textField2.setWidth("180px");
        textField3.setLabel("Description");
        textField3.setWidth("180px");
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("1100px");
        layoutRow2.setHeight("75px");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);
        textField4.setLabel("Price");
        textField4.setWidth("180px");
        textField5.setLabel("Quantity");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, textField5);
        textField5.setWidth("180px");
        textField6.setLabel("Voltage");
        textField6.setWidth("180px");
        layoutRow3.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("1100px");
        layoutRow3.setHeight("75px");
        layoutRow3.setAlignItems(Alignment.CENTER);
        layoutRow3.setJustifyContentMode(JustifyContentMode.CENTER);
        textField7.setLabel("Prouct Dimensions");
        textField7.setWidth("180px");
        textField8.setLabel("Item Weight");
        textField8.setWidth("180px");
        textField9.setLabel("Body Material");
        textField9.setWidth("180px");
        layoutRow4.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("1100px");
        layoutRow4.setHeight("75px");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.CENTER);
        textField10.setLabel("Item Model Number");
        textField10.setWidth("180px");
        textField11.setLabel("Design");
        textField11.setWidth("180px");
        textField12.setLabel("Colour");
        textField12.setWidth("180px");
        layoutRow5.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setWidth("1100px");
        layoutRow5.setHeight("75px");
        layoutRow5.setAlignItems(Alignment.CENTER);
        layoutRow5.setJustifyContentMode(JustifyContentMode.CENTER);
        textField13.setLabel("Batteries Required");
        textField13.setWidth("180px");
        textField14.setLabel("Image");
        textField14.setWidth("180px");
        comboBox.setLabel("Category");
        comboBox.setWidth("180px");
        setComboBoxSampleData(comboBox);
        layoutRow6.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow6);
        layoutRow6.addClassName(Gap.MEDIUM);
        layoutRow6.setWidth("1100px");
        layoutRow6.getStyle().set("flex-grow", "1");
        layoutRow6.setAlignItems(Alignment.CENTER);
        layoutRow6.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.setText("Cancel");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutRow);
        layoutRow.add(textField);
        layoutRow.add(textField2);
        layoutRow.add(textField3);
        getContent().add(layoutRow2);
        layoutRow2.add(textField4);
        layoutRow2.add(textField5);
        layoutRow2.add(textField6);
        getContent().add(layoutRow3);
        layoutRow3.add(textField7);
        layoutRow3.add(textField8);
        layoutRow3.add(textField9);
        getContent().add(layoutRow4);
        layoutRow4.add(textField10);
        layoutRow4.add(textField11);
        layoutRow4.add(textField12);
        getContent().add(layoutRow5);
        layoutRow5.add(textField13);
        layoutRow5.add(textField14);
        layoutRow5.add(comboBox);
        getContent().add(layoutRow6);
        layoutRow6.add(buttonPrimary);
        layoutRow6.add(buttonPrimary2);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }
}

