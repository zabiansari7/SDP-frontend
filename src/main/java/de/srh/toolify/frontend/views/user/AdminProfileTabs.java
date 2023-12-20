package de.srh.toolify.frontend.views.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.xmp.XMPException;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import de.srh.toolify.frontend.client.RestClient;
import de.srh.toolify.frontend.data.*;
import de.srh.toolify.frontend.utils.HelperUtil;
import de.srh.toolify.frontend.utils.PDFGen;
import de.srh.toolify.frontend.views.MainLayout;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@PageTitle("AdminProfile | Toolify")
@Route(value = "admin", layout = MainLayout.class)
@Uses(Icon.class)
public class AdminProfileTabs extends Composite<VerticalLayout> {
    private static final long serialVersionUID = 1L;

    Binder<User> binder = new Binder<>(User.class);
    Binder<Product> binderProduct = new Binder<>(Product.class);
    Binder<ProductForEdit> binderEditProduct = new Binder<>(ProductForEdit.class);
    Binder<Product>bindAddProduct = new Binder<>(Product.class);
	VerticalLayout adminDetailsMain = new VerticalLayout();
    FormLayout adminDetailsFormLayout = new FormLayout();
    TextField firstname = new TextField();
    TextField lastname = new TextField();
    EmailField email = new EmailField();
    TextField mobile = new TextField();
    TextField defaultStreetName = new TextField();
    TextField defaultStreetNumber = new TextField();
    TextField defaultPincode = new TextField();
    TextField defaultCity = new TextField();
    HorizontalLayout adminDetailsHorizontalLayout = new HorizontalLayout();
    Button adminDetailsEditButton = new Button();
    Button adminDetailsSaveButton = new Button();
    Button adminDetailsCancelButton = new Button();
    HorizontalLayout layoutRow = new HorizontalLayout();
    
	HorizontalLayout layoutRowDialog = new HorizontalLayout();
    TextField name = new TextField();
    TextField description = new TextField();
    TextField price = new TextField();
    HorizontalLayout layoutRow2 = new HorizontalLayout();
    TextField quantity = new TextField();
    TextField voltage = new TextField();
    TextField productDimensions = new TextField();
    HorizontalLayout layoutRow3 = new HorizontalLayout();
    TextField itemWeight = new TextField();
    TextField bodyMaterial = new TextField();
    TextField itemModelNumber = new TextField();
    HorizontalLayout layoutRow4 = new HorizontalLayout();
    TextField design = new TextField();
    TextField colour = new TextField();
    TextField batteriesRequired = new TextField();
    HorizontalLayout layoutRow5 = new HorizontalLayout();
    TextField image = new TextField();
    Select<Category> category = new Select<>();
    HorizontalLayout layoutRow6 = new HorizontalLayout();
    Button saveButton = new Button();
    Button cancelButton = new Button();
    
    String emailFromSession = HelperUtil.getEmailFromSession();
    Category categorySelectValue;

	public AdminProfileTabs() {
		binder.bindInstanceFields(this);
		binderProduct.bindInstanceFields(this);

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("100%");
        setTabSheetSampleData(tabSheet, binder);
        getContent().add(layoutRow);
        layoutRow.add(tabSheet);
    }

    private void setTabSheetSampleData(TabSheet tabSheet, Binder<User> binder) {
        tabSheet.add("Admin Details", new Div(getAdminDetailsLayout(binder))).addClassName("tabStyle");
        tabSheet.add("Users Order History", new Div(getAdminOrdersLayout())).addClassName("tabStyle");
        tabSheet.add("Manage Products", new Div(getManageProductsLayout(binderProduct))).addClassName("tabStyle");
        tabSheet.add("Manage Category", new Div(getManageCategoryLayout())).addClassName("tabStyle");
    }

    private VerticalLayout getAdminDetailsLayout(Binder<User> binder) {
	    getContent().setWidth("100%"); 
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        adminDetailsMain.setWidth("100%");
        adminDetailsMain.setMaxWidth("800px");
        adminDetailsMain.setHeight("min-content");
        adminDetailsFormLayout.setWidth("100%");
        
        firstname.setLabel("First Name");
        firstname.setValueChangeMode(ValueChangeMode.EAGER);
        firstname.setPattern("^[a-zA-Z]*$");
        firstname.setMaxLength(30);
        firstname.setRequiredIndicatorVisible(true);
        firstname.setClearButtonVisible(true);

        lastname.setLabel("Last Name");
        lastname.setValueChangeMode(ValueChangeMode.EAGER);
        lastname.setMaxLength(30);
        lastname.setRequiredIndicatorVisible(true);
        lastname.setPattern("^[a-zA-Z]*$");

        email.setLabel("Email");
        
        mobile.setLabel("Mobile");
        mobile.setPattern("^\\+\\d{0,15}$");
        mobile.setRequiredIndicatorVisible(true);
        mobile.setMaxLength(15);
        mobile.addValueChangeListener(event -> {
            String value = event.getValue();
            boolean isValid = value.matches("^\\+\\d{0,15}$");
            mobile.setInvalid(!isValid);
            if (isValid) {
            	mobile.setHelperText("");
			} else {
				mobile.setHelperText("Mobile number should start with '+' and then only 15 numbers");
				
			}
        });

        defaultStreetName.setLabel("Street");
        defaultStreetName.setValueChangeMode(ValueChangeMode.EAGER);
        defaultStreetName.setRequiredIndicatorVisible(true);
        defaultStreetName.setPattern("^[a-zA-Z]*$");
        defaultStreetName.setMaxLength(30);

        defaultStreetNumber.setLabel("Number");
        defaultStreetNumber.setPattern("\\d{0,3}");
        defaultStreetNumber.setMaxLength(3);
        defaultStreetNumber.setWidth("min-content");
        defaultStreetNumber.setValueChangeMode(ValueChangeMode.EAGER);
        
        defaultPincode.setLabel("Pincode");
        defaultPincode.setValueChangeMode(ValueChangeMode.EAGER);
        defaultPincode.setPattern("\\d{0,5}");
        defaultPincode.setWidth("min-content");
        defaultPincode.setMaxLength(5);
      
        defaultCity.setLabel("City");
        defaultCity.setWidth("min-content");
        defaultCity.setValueChangeMode(ValueChangeMode.EAGER);
        defaultCity.setPattern("^[a-zA-Z ]*$");
        defaultCity.setMaxLength(30);
        defaultCity.setRequiredIndicatorVisible(true);
        
        adminDetailsHorizontalLayout.addClassName(Gap.MEDIUM);
        adminDetailsHorizontalLayout.setWidth("100%");
        adminDetailsHorizontalLayout.getStyle().set("flex-grow", "1");
        adminDetailsEditButton.setText("Edit");
        adminDetailsEditButton.setWidth("min-content");
        adminDetailsEditButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        adminDetailsSaveButton.setText("Save");
        adminDetailsSaveButton.setWidth("min-content");
        adminDetailsSaveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        adminDetailsCancelButton.setText("Cancel");
        adminDetailsCancelButton.setWidth("min-content");
        getContent().add(adminDetailsMain);
        adminDetailsMain.add(adminDetailsFormLayout);
        adminDetailsFormLayout.add(firstname);
        adminDetailsFormLayout.add(lastname);
        adminDetailsFormLayout.add(email);
        adminDetailsFormLayout.add(mobile);
        adminDetailsFormLayout.add(defaultStreetName);
        adminDetailsFormLayout.add(defaultStreetNumber);
        adminDetailsFormLayout.add(defaultPincode);
        adminDetailsFormLayout.add(defaultCity);
        adminDetailsMain.add(adminDetailsHorizontalLayout);
        adminDetailsHorizontalLayout.add(adminDetailsEditButton);
        
        User admin = getUserByEmail();
        binder.setBean(admin);
        binder.setReadOnly(true);


        adminDetailsEditButton.addClickListener(e -> {
        	binder.setReadOnly(false);
        	email.setReadOnly(true);
        	binder.getFields().forEach(field -> field.setRequiredIndicatorVisible(true));
        	email.setRequiredIndicatorVisible(false);
        	adminDetailsHorizontalLayout.removeAll();
        	adminDetailsHorizontalLayout.add(adminDetailsSaveButton, adminDetailsCancelButton);
        });
        
        adminDetailsCancelButton.addClickListener(e -> {
        	binder.setReadOnly(true);
        	adminDetailsHorizontalLayout.removeAll();
        	adminDetailsHorizontalLayout.add(adminDetailsEditButton);
        	UI.getCurrent().getPage().reload();
        });
        
        adminDetailsSaveButton.addClickListener(e -> {
        	if (binder.validate().isOk() == false) {
        		showNotification("Please correct your input", NotificationVariant.LUMO_ERROR);
    			return;
			}
        	if (binder.getFields().anyMatch(a -> a.isEmpty())) {
    			showNotification("Empty fields detected !", NotificationVariant.LUMO_ERROR);
    			return;
    		}        	
        	String encodedEmail;
            encodedEmail = URLEncoder.encode(emailFromSession, StandardCharsets.UTF_8);

            EditUser editUser = prepareEditUser();
        	ResponseData data = RestClient.requestHttp("PUT", "http://localhost:8080/private/user?email=" + encodedEmail, editUser, EditUser.class);
            try {
                if (data.getConnection().getResponseCode() != 201) {
                    HelperUtil.showNotification("Error occurred while updating user", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
                } else {
                    adminDetailsHorizontalLayout.removeAll();
                    adminDetailsHorizontalLayout.add(adminDetailsEditButton);
                    binder.setReadOnly(true);
                    HelperUtil.showNotification("Your details has been updated successfully", NotificationVariant.LUMO_SUCCESS, Position.TOP_CENTER);
                }
            } catch (IOException ex) {
                HelperUtil.showNotification("Error occurred while updating user", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
                throw new RuntimeException(ex);
            }
        });
        
        return adminDetailsMain;
	}

	private EditUser prepareEditUser() {
		EditUser editUser = new EditUser();
		editUser.setFirstname(firstname.getValue());
		editUser.setLastname(lastname.getValue());
		editUser.setMobile(mobile.getValue());
		editUser.setDefaultStreetName(defaultStreetName.getValue());
		editUser.setDefaultStreetNumber(Long.valueOf(defaultStreetNumber.getValue()));
		editUser.setDefaultPincode(Long.valueOf(defaultPincode.getValue()));
		editUser.setDefaultCity(defaultCity.getValue());	
		return editUser;
}


	private void showNotification(String text, NotificationVariant variant) {
		Notification notification = Notification
				.show(text);
		notification.setDuration(5000);
		notification.setPosition(Position.TOP_CENTER);
		notification.addThemeVariants(variant);
    }
	
	private User getUserByEmail() {
    	String encodedEmail = URLEncoder.encode(emailFromSession, StandardCharsets.UTF_8);
        ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/private/user?email=" + encodedEmail, null, null);
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

	private VerticalLayout getAdminOrdersLayout() {    	
		VerticalLayout main = new VerticalLayout();
		
		HorizontalLayout labelHorizontalLayout = headerOrderHistoryLayout();
		main.add(labelHorizontalLayout);
		
		JsonNode orderListJsonNode = getPurchaseHistoryAll();
		
		List<PurchaseHistory> purchaseHistories = HelperUtil.sortByTimeDescending(orderListJsonNode);
	
		int count = 0;    	
		for (PurchaseHistory purchaseHistory : purchaseHistories) {
			count++;
			HorizontalLayout itemHorizontalLayout = createHorizontalLayout();
            itemHorizontalLayout.setHeight("47px");
            if (count%2 != 0) {
                itemHorizontalLayout.getStyle().set("background-color","lightcyan");
            } else {
                itemHorizontalLayout.getStyle().set("background-color","whitesmoke");
            }
			itemHorizontalLayout.add(createLabel(String.valueOf(count)),
					createLabel(purchaseHistory.getUser().getEmail()),
					createLabel(String.valueOf(purchaseHistory.getDate()).replace("T", " Time:").replace("Z", " ")),
					createLabel(String.valueOf(purchaseHistory.getInvoice())),
					createButton(purchaseHistory.getInvoice(), main));
			main.add(itemHorizontalLayout);
		}
		
	    return main;
	}
	
	private HorizontalLayout headerOrderHistoryLayout() {
		HorizontalLayout h = createHorizontalLayout();
		h.add(createHeader("SrNo."), createHeader("User Email"),createHeader("Purchase Date"), createHeader("Invoice Number"), createHeader("View Invoice"));
		return h;
	}

    private HorizontalLayout headerManageCategoryLayout(VerticalLayout main) {
        HorizontalLayout h = createHorizontalLayout();
        h.add(createHeader("SrNo."), createHeader("Category"), createAddCategoryButton(main));
        return h;
    }

	private HorizontalLayout headerManageProductsLayout(VerticalLayout mainLayout) {
		HorizontalLayout h = createHorizontalLayout();
		h.add(createHeader("SrNo."), createHeader("Product Name"),createHeader("Quantity"), createHeader("Price"), createAddProductButton(mainLayout));
		return h;
	}
	
	private Button createAddProductButton(VerticalLayout mainLayout) {
		Button button = new Button("Add Product");
		button.setWidth("20%");
		button.addClassName("clickable-button");
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		button.addClickListener(event -> {
			Dialog dialog = new Dialog();
			dialog.getElement().setAttribute("aria-label", "Create new product");
			mainLayout.add(dialog);
			dialog.add(createAddProductDialog(dialog, mainLayout));
			dialog.open();
		});
		return button;
	}
	
	private HorizontalLayout createHorizontalLayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidthFull();
		horizontalLayout.setHeight("25px");
		return horizontalLayout;
	}
	
	private H4 createLabel(String text) {
		H4 label = new H4(text);
		label.setWidth("20%");
		label.setWidthFull();
        label.addClassName(Padding.MEDIUM);
		return label;
	}
	
	private Button createButton(int invoiceNo, VerticalLayout main) {
		Button button = new Button("Downlaod PDF");
		button.getElement().setProperty("invoiceNo", invoiceNo);
		button.setWidth("20%");
		button.addClassName("clickable-button");
        button.addClickListener(event -> {
            PurchaseHistory purchaseHistory = HelperUtil.getPurchaseByInvoice(invoiceNo);
            Anchor pdf;
            try {
                PDFGen app = new PDFGen();
                pdf = app.createPdf(purchaseHistory);
            } catch (DocumentException | IOException | XMPException e) {
                throw new RuntimeException(e);
            }
            showNotification(String.format("PDF for invoice number '%d' generated successfully", invoiceNo), NotificationVariant.LUMO_SUCCESS);
            getElement().executeJs("window.open($0.href, '_blank')", pdf.getElement());
            main.add(pdf);

        });
		return button;
	}
	
	private H3 createHeader(String text) {
    	H3 header = new H3(text);
		header.setWidthFull();
		return header;
	}
	
	private HorizontalLayout createEditDeleteButtonLayout(Binder<Product> binderProduct, VerticalLayout mainLayout, Long productId) {
		HorizontalLayout h = createHorizontalLayout();
        h.setWidth("115%");
		h.add(createEditButton(binderProduct, mainLayout, productId), createDeleteButton(mainLayout, productId));
		return h;
	}
	
	private Button createEditButton(Binder<Product> binderProduct, VerticalLayout mainLayout, Long productId) {
		Button button = new Button("Edit");
        button.getElement().setProperty("productId", String.valueOf(productId));
		button.setWidth("50%");
		button.addClassName("clickable-button");
        button.addClickListener(e -> {
            Dialog dialog = new Dialog();
            dialog.getElement().setAttribute("aria-label", "Edit Product");
            mainLayout.add(dialog);
            dialog.add(createEditProductDialog(binderProduct, productId, dialog, mainLayout));
            dialog.open();
        });
		return button;
	}
	
	private Button createDeleteButton(VerticalLayout main, Long productId) {
		Button button = new Button("Delete");
        button.getElement().setAttribute("productId", String.valueOf(productId));
		button.setWidth("50%");
		button.addClassName("clickable-button");
		button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.addClickListener(e -> {
            ResponseData data = deleteProductById(productId);
            try {
                if (data.getConnection().getResponseCode() == 201){
                    button.getElement().getParent().getParent().removeFromParent();
                    HelperUtil.showNotification("Product Deleted successfully", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                } else {
                    if (data.getNode().get("message").toString().contains("a foreign key constraint fails")) {
                        HelperUtil.showNotification("Cannot delete this Product. This Product is attached to a Purchase", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                    } else {
                        HelperUtil.showNotification("Error occurred while deleting the Product", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                    }
                }
            } catch (IOException ex) {
                HelperUtil.showNotification("Error occurred while deleting the Product", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                throw new RuntimeException(ex);
            }
        });
		return button;
	}

    private Button createAddCategoryButton(VerticalLayout mainLayout){
        Button button = new Button("Add Category");
        button.setWidth("20%");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClassName("clickable-button");
        button.addClickListener(event -> {
            Dialog dialog = new Dialog();
            dialog.getElement().setAttribute("aria-label", "Create new product");
            mainLayout.add(dialog);
            dialog.add(createAddCategoryDialog(dialog, mainLayout));
            dialog.open();

        });
        return button;
    }

    private VerticalLayout createAddCategoryDialog(Dialog dialog, VerticalLayout mainLayout) {
        VerticalLayout dialogVerticalLayout = new VerticalLayout();

        dialogVerticalLayout.setWidth("100%");
        dialogVerticalLayout.getStyle().set("flex-grow", "1");

        HorizontalLayout layoutRow = new HorizontalLayout();
        TextField categoryName = new TextField();
        categoryName.setLabel("Category");
        categoryName.setMaxLength(30);
        categoryName.setRequiredIndicatorVisible(true);

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setWidth("min-content");
        layoutRow.add(categoryName);
        layoutRow2.add(saveButton, cancelButton);
        dialogVerticalLayout.add(layoutRow, layoutRow2);

        layoutRow.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setHeight("75px");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);

        layoutRow2.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setHeight("75px");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);

        cancelButton.addClickListener(event -> {
           dialog.close();
        });

        saveButton.addClickListener(event -> {
            if (categoryName.getValue().isEmpty() || categoryName.getValue().isBlank()) {
                HelperUtil.showNotification("Empty Field Detected !", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                return;
            }
            addCategory(categoryName.getValue());
            showNotification("Category saved successfully", NotificationVariant.LUMO_SUCCESS);
            dialog.close();
            mainLayout.removeAll();
            mainLayout.getElement().executeJs("location.reload(true)");

        });

        return dialogVerticalLayout;

    }

    private void addCategory(String name){
        Category category = new Category();
        category.setCategoryName(name);
        ResponseData data = RestClient.requestHttp("POST", "http://localhost:8080/private/admin/categories/category", category, Category.class);
        try {
            if (data.getConnection().getResponseCode() != 201) {
                HelperUtil.showNotification("Error occurred while saving category", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred while saving category", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
    }

    private Button createEditCategoryButton(VerticalLayout main, Category category){
        Button button = new Button("Edit");
        button.setWidth("10%");
        button.addClassName("clickable-button");
        button.addClickListener(event -> {
            Dialog dialog = new Dialog();
            dialog.getElement().setAttribute("aria-label", "Edit Category");
            main.add(dialog);
            dialog.add(createEditCategoryDialog(dialog, main, category));
            dialog.open();
        });
        return button;
    }

    private VerticalLayout createEditCategoryDialog(Dialog dialog, VerticalLayout mainLayout, Category category) {
        VerticalLayout dialogVerticalLayout = new VerticalLayout();

        dialogVerticalLayout.setWidth("100%");
        dialogVerticalLayout.getStyle().set("flex-grow", "1");

        HorizontalLayout layoutRow = new HorizontalLayout();
        TextField categoryName = new TextField();
        categoryName.setLabel("Category");
        categoryName.setValue(category.getCategoryName());
        categoryName.setMaxLength(30);
        categoryName.setRequiredIndicatorVisible(true);

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setWidth("min-content");
        layoutRow.add(categoryName);
        layoutRow2.add(saveButton, cancelButton);
        dialogVerticalLayout.add(layoutRow, layoutRow2);

        layoutRow.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setHeight("75px");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);

        layoutRow2.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setHeight("75px");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);

        cancelButton.addClickListener(event -> {
            dialog.close();
        });

        saveButton.addClickListener(event -> {
            if (categoryName.getValue().isEmpty() || categoryName.getValue().isBlank()) {
                showNotification("Empty Field Detected !", NotificationVariant.LUMO_ERROR);
                return;
            }
            editCategory(category.getCategoryId(), categoryName.getValue());
            showNotification("Category updated successfully", NotificationVariant.LUMO_SUCCESS);
            dialog.close();
            mainLayout.removeAll();
            mainLayout.getElement().executeJs("location.reload(true)");

        });
        return dialogVerticalLayout;
    }

    private void editCategory(Long categoryId, String categoryName){
        CategoryForEdit category = new CategoryForEdit();
        category.setCategoryName(categoryName);
        ResponseData data = RestClient.requestHttp("PUT", "http://localhost:8080/private/admin/categories/category/" + categoryId, category, CategoryForEdit.class);
        try {
            if (data.getConnection().getResponseCode() != 200) {
                HelperUtil.showNotification("Error occurred updating the category", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred updating the category", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
    }


    private Button createDeleteCategoryButton(Long categoryId){
        Button button = new Button("Delete");
        button.setWidth("10%");
        button.addClassName("clickable-button");
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.addClickListener(event -> {
            ResponseData data = deleteCategoryById(categoryId);
            try {
                if (data.getConnection().getResponseCode() == 201){
                    button.getElement().getParent().removeFromParent();
                    HelperUtil.showNotification("Category deleted successfully", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                } else {
                    if (data.getNode().get("message").toString().contains("a foreign key constraint fails")) {
                        HelperUtil.showNotification("Cannot delete this Category. This Category is attached to an existing Product", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                    } else {
                        HelperUtil.showNotification("Error occurred while deleting the Category !!", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                    }
                }
            } catch (IOException e) {
                HelperUtil.showNotification("Error occurred while deleting the Category !!", NotificationVariant.LUMO_ERROR, Position.TOP_CENTER);
                throw new RuntimeException(e);
            }});
        return button;
    }

    private ResponseData deleteCategoryById(Long categoryId) {
        ResponseData data = RestClient.requestHttp("DELETE", "http://localhost:8080/private/admin/categories/category/" + categoryId, null, null);
        return data;
    }

    private ResponseData deleteProductById(Long productId) {
        ResponseData data = RestClient.requestHttp("DELETE", "http://localhost:8080/private/admin/products/product/" + productId, null, null);
        return data;
    }

    private VerticalLayout getManageProductsLayout(Binder<Product> binderProduct) {
    	VerticalLayout main = new VerticalLayout();
    	
    	HorizontalLayout labeHorizontalLayout = headerManageProductsLayout(main);
    	main.add(labeHorizontalLayout);
        main.setWidth("100%");
        
        JsonNode productsNode = getAllProducts();
        ObjectMapper mapper = HelperUtil.getObjectMapper();
    	int count = 0;
    	for (JsonNode productNode : Objects.requireNonNull(productsNode)) {
    		count++;
    		Product product = mapper.convertValue(productNode, Product.class);
			
			HorizontalLayout productHorizontalLayout = createHorizontalLayout();
            productHorizontalLayout.setHeight("47px");
            if (count%2 != 0) {
                productHorizontalLayout.getStyle().set("background-color","lightcyan");
            } else {
                productHorizontalLayout.getStyle().set("background-color","whitesmoke");
            }
			productHorizontalLayout.add(
					createLabel(String.valueOf(count)), 
					createLabel(product.getName()), 
					createLabel(String.valueOf(product.getQuantity())), 
					createLabel("€" + String.valueOf(product.getPrice())),
					createEditDeleteButtonLayout(binderProduct, main, product.getProductId()));
					main.add(productHorizontalLayout);
    	}
        return main;
	}
	
	private JsonNode getPurchaseHistoryAll() {
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/private/admin/purchases/history/all", null, null);
        try {
            if (data.getConnection().getResponseCode() != 200) {
                HelperUtil.showNotification("Error occurred while processing purchase history", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            } else {
                JsonNode purchaseHistoryNode = data.getNode();
                return purchaseHistoryNode;
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred while processing purchase history", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
		return null;
	}
	
	private VerticalLayout createAddProductDialog(Dialog dialog, VerticalLayout main) {
		VerticalLayout dialogVerticalLayout = new VerticalLayout();
		
		bindAddProduct.forField(name).bind(Product::getName, Product::setName);
		bindAddProduct.forField(description).bind(Product::getDescription, Product::setDescription);
		bindAddProduct.forField(quantity).bind(Product::getQuantity, Product::setQuantity);
		bindAddProduct.forField(image).bind(Product::getImage, Product::setImage);

		
        dialogVerticalLayout.setWidth("100%");
        dialogVerticalLayout.getStyle().set("flex-grow", "1");
        layoutRowDialog.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRowDialog);
        layoutRowDialog.addClassName(Gap.MEDIUM);
        layoutRowDialog.setHeight("75px");
        layoutRowDialog.setAlignItems(Alignment.CENTER);
        layoutRowDialog.setJustifyContentMode(JustifyContentMode.CENTER);

        name.setLabel("Name");
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.setRequiredIndicatorVisible(true);

        description.setLabel("Description");
        description.setValueChangeMode(ValueChangeMode.EAGER);
        description.setRequiredIndicatorVisible(true);

        price.setLabel("Price");
        price.setValueChangeMode(ValueChangeMode.EAGER);
        price.setRequiredIndicatorVisible(true);
        price.setPattern("\\d+(\\.\\d{2})?");
        price.setMaxLength(8);

        layoutRow2.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidthFull();
        layoutRow2.setHeight("75px");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);

        quantity.setLabel("Quantity");
        quantity.setRequiredIndicatorVisible(true);
        quantity.setValueChangeMode(ValueChangeMode.EAGER);
        quantity.setMaxLength(3);
        quantity.setPattern("\\d{0,3}");

        voltage.setLabel("Voltage");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, voltage);

        productDimensions.setLabel("Product Dimensions");

        layoutRow3.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setHeight("75px");
        layoutRow3.setAlignItems(Alignment.CENTER);
        layoutRow3.setJustifyContentMode(JustifyContentMode.CENTER);

        itemWeight.setLabel("Item Weight");
        bodyMaterial.setLabel("Body Material");
        itemModelNumber.setLabel("Item Model Number");

        layoutRow4.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setHeight("75px");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.CENTER);

        design.setLabel("Design");
        colour.setLabel("Colour");
        batteriesRequired.setLabel("Batteries Required");

        layoutRow5.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setHeight("75px");
        layoutRow5.setAlignItems(Alignment.CENTER);
        layoutRow5.setJustifyContentMode(JustifyContentMode.CENTER);

        image.setLabel("Image URL");
        image.setValueChangeMode(ValueChangeMode.EAGER);
        image.setRequiredIndicatorVisible(true);

        category.setLabel("Category");
        category.setPlaceholder("Select Category");
        category.setRequiredIndicatorVisible(true);
        List<Category> categories = HelperUtil.getAllCategoriesAsClass();
        category.setItems(categories);
        category.addValueChangeListener(e -> this.setCategorySelectValue(e.getValue()));

        layoutRow6.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow6);
        layoutRow6.addClassName(Gap.MEDIUM);
        layoutRow6.getStyle().set("flex-grow", "1");
        layoutRow6.setAlignItems(Alignment.CENTER);
        layoutRow6.setJustifyContentMode(JustifyContentMode.CENTER);

        saveButton.setText("Save");
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton.setText("Cancel");
        cancelButton.setWidth("min-content");
        cancelButton.addClickListener(e -> {
        	dialog.close();
        });

        dialogVerticalLayout.add(layoutRowDialog);
        layoutRowDialog.add(name);
        layoutRowDialog.add(description);
        layoutRowDialog.add(price);
        dialogVerticalLayout.add(layoutRow2);
        layoutRow2.add(quantity);
        layoutRow2.add(voltage);
        layoutRow2.add(productDimensions);
        dialogVerticalLayout.add(layoutRow3);
        layoutRow3.add(itemWeight);
        layoutRow3.add(bodyMaterial);
        layoutRow3.add(itemModelNumber);
        dialogVerticalLayout.add(layoutRow4);
        layoutRow4.add(design);
        layoutRow4.add(colour);
        layoutRow4.add(batteriesRequired);
        dialogVerticalLayout.add(layoutRow5);
        layoutRow5.add(image);
        layoutRow5.add(category);
        dialogVerticalLayout.add(layoutRow6);
        layoutRow6.add(saveButton);
        layoutRow6.add(cancelButton);

        category.setItemLabelGenerator(Category::getCategoryName);

        Product product = new Product();
        binderProduct.setBean(product);
        
        saveButton.addClickListener(e -> {
            if (bindAddProduct.validate().isOk() == false) {
                showNotification("Invalid Input !", NotificationVariant.LUMO_ERROR);
                return;
            }
            if (bindAddProduct.getFields().anyMatch(field -> field.isEmpty())) {
                showNotification("Empty Fields Detected !", NotificationVariant.LUMO_ERROR);
                return;
            }
        	if (price.getValue().isEmpty() || price.getValue().isBlank()) {
        		showNotification("Empty Fields Detected !", NotificationVariant.LUMO_ERROR);
				return;
			}
        	if (!price.getValue().matches("\\d+(\\.\\d{2})?")) {
        	    showNotification("Invalid Price Input !", NotificationVariant.LUMO_ERROR);
        	    return;
        	}

        	addProduct(binderProduct);
        	HelperUtil.showNotification("Product saved successfully", NotificationVariant.LUMO_SUCCESS, Position.TOP_CENTER);
        	dialog.close();
        	main.removeAll();
        	main.getElement().executeJs("location.reload(true)");
        });
                
		return dialogVerticalLayout;
	}
	
	private  JsonNode addProduct(Binder<Product> productBinder) {
		Product product = productBinder.getBean();
		ResponseData data = RestClient.requestHttp("POST","http://localhost:8080/private/admin/products/product", product, Product.class);
        try {
            if (data.getConnection().getResponseCode() != 201) {
                HelperUtil.showNotification("Error saving the Product", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            } else {
                JsonNode productResponse = data.getNode();
                return productResponse;
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error saving the Product", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
		return null;
	}
	
	private JsonNode getAllProducts() {
		ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/public/products/all", null, null);
        try {
            if (data.getConnection().getResponseCode() != 200) {
                HelperUtil.showNotification("Error occurred while processing products", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            } else {
                JsonNode productsNode = data.getNode();
                return productsNode;
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred while processing products", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
		return null;
	}

	public Category getCategorySelectValue() {
		return categorySelectValue;
	}

	public void setCategorySelectValue(Category categorySelectValue) {
		this.categorySelectValue = categorySelectValue;
	}

    private VerticalLayout getManageCategoryLayout() {
        VerticalLayout main = new VerticalLayout();
        main.setWidth("50%");

        HorizontalLayout labelHorizontalLayout = headerManageCategoryLayout(main);
        labelHorizontalLayout.setWidthFull();
        main.add(labelHorizontalLayout);

        List<Category> categories = HelperUtil.getAllCategoriesAsClass();

        int count = 0;
        for (Category category : categories) {
            count++;
            HorizontalLayout hz = createHorizontalLayout();
            hz.setHeight("47px");
            if (count%2 != 0) {
                hz.getStyle().set("background-color","lightcyan");
            } else {
                hz.getStyle().set("background-color","whitesmoke");
            }
            hz.add(createLabel(String.valueOf(count)), createLabel(category.getCategoryName()), createEditCategoryButton(main, category), createDeleteCategoryButton(category.getCategoryId()));
            main.add(hz);
        }

        return main;
    }

    private VerticalLayout createEditProductDialog(Binder<Product> binderProduct, Long productId,  Dialog dialog, VerticalLayout main) {
        JsonNode productNode = getProductById(productId);
        ObjectMapper mapper = HelperUtil.getObjectMapper();
        ProductForEdit product = mapper.convertValue(productNode, ProductForEdit.class);
        binderProduct.setBean(product);
        category.setPlaceholder(Objects.requireNonNull(productNode).get("category").get("categoryName").textValue());

        VerticalLayout dialogVerticalLayout = new VerticalLayout();

        binderEditProduct.forField(name).bind(ProductForEdit::getName, ProductForEdit::setName);
        binderEditProduct.forField(description).bind(ProductForEdit::getDescription, ProductForEdit::setDescription);
        binderEditProduct.forField(quantity).bind(ProductForEdit::getQuantity, ProductForEdit::setQuantity);
        binderEditProduct.forField(image).bind(ProductForEdit::getImage, ProductForEdit::setImage);

        dialogVerticalLayout.setWidth("100%");
        dialogVerticalLayout.getStyle().set("flex-grow", "1");
        layoutRowDialog.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRowDialog);
        layoutRowDialog.addClassName(Gap.MEDIUM);
        layoutRowDialog.setHeight("75px");
        layoutRowDialog.setAlignItems(Alignment.CENTER);
        layoutRowDialog.setJustifyContentMode(JustifyContentMode.CENTER);

        name.setLabel("Name");
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.setRequiredIndicatorVisible(true);

        description.setLabel("Description");
        description.setValueChangeMode(ValueChangeMode.EAGER);
        description.setRequiredIndicatorVisible(true);

        price.setLabel("Price");
        price.setValueChangeMode(ValueChangeMode.EAGER);
        price.setRequiredIndicatorVisible(true);
        price.setPattern("\\d+(\\.\\d{2})?");
        price.setMaxLength(8);

        layoutRow2.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidthFull();
        layoutRow2.setHeight("75px");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);

        quantity.setLabel("Quantity");
        quantity.setPattern("\\d{0,3}");
        quantity.setValueChangeMode(ValueChangeMode.EAGER);
        quantity.setRequiredIndicatorVisible(true);
        quantity.setMaxLength(3);

        voltage.setLabel("Voltage");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, voltage);

        productDimensions.setLabel("Product Dimensions");

        layoutRow3.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow3);

        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setHeight("75px");
        layoutRow3.setAlignItems(Alignment.CENTER);
        layoutRow3.setJustifyContentMode(JustifyContentMode.CENTER);

        itemWeight.setLabel("Item Weight");
        bodyMaterial.setLabel("Body Material");
        itemModelNumber.setLabel("Item Model Number");

        layoutRow4.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setHeight("75px");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.CENTER);

        design.setLabel("Design");
        colour.setLabel("Colour");
        batteriesRequired.setLabel("Batteries Required");

        layoutRow5.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setHeight("75px");
        layoutRow5.setAlignItems(Alignment.CENTER);
        layoutRow5.setJustifyContentMode(JustifyContentMode.CENTER);

        image.setLabel("Image URL");
        image.setValueChangeMode(ValueChangeMode.EAGER);
        image.setRequiredIndicatorVisible(true);

        category.setLabel("Category");
        category.setRequiredIndicatorVisible(true);
        category.setValue(product.getCategory());
        category.setPlaceholder(productNode.get("category").get("categoryName").textValue());
        List<Category> categories = HelperUtil.getAllCategoriesAsClass();
        category.setItems(categories);
        category.addValueChangeListener(e -> {
            this.setCategorySelectValue(e.getValue());
            category.getElement().setProperty("newCategoryId", e.getValue().getCategoryId());
        });

        layoutRow6.setWidthFull();
        dialogVerticalLayout.setFlexGrow(1.0, layoutRow6);
        layoutRow6.addClassName(Gap.MEDIUM);
        layoutRow6.getStyle().set("flex-grow", "1");
        layoutRow6.setAlignItems(Alignment.CENTER);
        layoutRow6.setJustifyContentMode(JustifyContentMode.CENTER);

        saveButton.setText("Save");
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton.setText("Cancel");
        cancelButton.setWidth("min-content");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addClickListener(e -> {
            dialog.close();
        });

        dialogVerticalLayout.add(layoutRowDialog);
        layoutRowDialog.add(name);
        layoutRowDialog.add(description);
        layoutRowDialog.add(price);
        dialogVerticalLayout.add(layoutRow2);
        layoutRow2.add(quantity);
        layoutRow2.add(voltage);
        layoutRow2.add(productDimensions);
        dialogVerticalLayout.add(layoutRow3);
        layoutRow3.add(itemWeight);
        layoutRow3.add(bodyMaterial);
        layoutRow3.add(itemModelNumber);
        dialogVerticalLayout.add(layoutRow4);
        layoutRow4.add(design);
        layoutRow4.add(colour);
        layoutRow4.add(batteriesRequired);
        dialogVerticalLayout.add(layoutRow5);
        layoutRow5.add(image);
        layoutRow5.add(category);
        dialogVerticalLayout.add(layoutRow6);
        layoutRow6.add(saveButton);
        layoutRow6.add(cancelButton);

        category.setItemLabelGenerator(Category::getCategoryName);
        product.setProductId(productId);

        saveButton.addClickListener(e -> {
            if (binderEditProduct.validate().isOk() == false) {
                showNotification("Invalid Input !", NotificationVariant.LUMO_ERROR);
                return;
            }
            if (binderEditProduct.getFields().anyMatch(field -> field.isEmpty())) {
                showNotification("Empty Fields Detected !", NotificationVariant.LUMO_ERROR);
                return;
            }
            if (price.getValue().isEmpty() || price.getValue().isBlank()) {
                showNotification("Empty Fields Detected !", NotificationVariant.LUMO_ERROR);
                return;
            }
            if (!price.getValue().matches("\\d+(\\.\\d{2})?")) {
                showNotification("Invalid Price Input !", NotificationVariant.LUMO_ERROR);
                return;
            }

            if (this.getCategorySelectValue() == null) {
                product.setCategoryTo(productNode.get("category").get("categoryId").asLong());
            } else {
                product.setCategoryTo(this.getCategorySelectValue().getCategoryId());
            }
            editProduct(productId);

            HelperUtil.showNotification("Product updated successfully", NotificationVariant.LUMO_SUCCESS, Position.TOP_CENTER);
            dialog.close();
            main.removeAll();
            main.getElement().executeJs("location.reload(true)");
        });

        return dialogVerticalLayout;
    }

    private JsonNode editProduct(Long productId) {
        ResponseData data = RestClient.requestHttp("PUT", "http://localhost:8080/private/admin/products/product/" + productId, binderProduct.getBean(), ProductForEdit.class);
        try {
            if (data.getConnection().getResponseCode() != 201) {
                HelperUtil.showNotification("Error occurred while updating product", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            } else {
                return data.getNode();
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred while updating product", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
        return null;
    }

    private JsonNode getProductById(Long productId) {
        ResponseData data = RestClient.requestHttp("GET", "http://localhost:8080/public/products/product/" + productId, null, null);
        try {
            if (data.getConnection().getResponseCode() != 200) {
                HelperUtil.showNotification("Error occurred while processing product information", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            } else {
                JsonNode productNode = data.getNode();
                return productNode;
            }
        } catch (IOException e) {
            HelperUtil.showNotification("Error occurred while processing product information", NotificationVariant.LUMO_ERROR, Notification.Position.TOP_CENTER);
            throw new RuntimeException(e);
        }
        return null;
    }
}