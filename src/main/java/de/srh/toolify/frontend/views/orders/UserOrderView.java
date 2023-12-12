package de.srh.toolify.frontend.views.orders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.hierarchy.AbstractHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Route("table")
public class UserOrderView extends VerticalLayout {

	private List<Purchase> purchases = createSamplePurchases();
    public UserOrderView() {
//    	  getStyle().set("theme", "toolify-frontend");
//        // Create a grid with a bean type (in this case, a simple Purchase class)
//        Grid<Purchase> grid = new Grid<>(Purchase.class);
//        grid.setColumns("nr", "date", "totalPrice", "invoice");
//
//        // Add some columns to the grid
////        grid.addColumn("nr").setHeader("Nr");
////        grid.addColumn("date").setHeader("Date");
////        grid.addColumn("totalPrice").setHeader("Total Price");
////        grid.addColumn("invoice").setHeader("Invoice");
//
//        // Create some example data
//        grid.setItems(
//                new Purchase(1, LocalDate.of(2023, 1, 15), 100.0, "INV-001", createPurchaseItems(1)),
//                new Purchase(2, LocalDate.of(2023, 2, 20), 150.0, "INV-002", createPurchaseItems2(2)),
//                new Purchase(3, LocalDate.of(2023, 3, 25), 200.0, "INV-003", createPurchaseItems3(3))
//        );
//
//        // Add the grid to the layout
//        add(grid);
//
//        // Set up an accordion for each purchase
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            removeAll(); // Clear the layout before adding components
//            if (event.getValue() != null) {
//                Purchase selectedPurchase = event.getValue();
//                TreeGrid<PurchaseItem> treeGrid = createPurchaseItemsTreeGrid(selectedPurchase);
//                add(treeGrid);
//            }
//                add(grid);
//            
//        });
//    	TreeGrid<Purchase> treeGrid = new TreeGrid<>();
//        treeGrid.setItems(createDataProvider(), Purchase::getNr);
//        treeGrid.addHierarchyColumn(Purchase::getInvoice)
//                .setHeader("Invoice");
//        treeGrid.addColumn(Purchase::getTotalPrice).setHeader("Total Price");
//        treeGrid.addColumn(Purchase::getDate).setHeader("Date");
//        // end::snippet[]
//        add(treeGrid);
    	
    	
    
   
    }
    
    private List<Purchase> getPurchaseItem(Purchase purchase) {
		return null;
	}

    // Create a Grid for displaying purchase items
    private TreeGrid<PurchaseItem> createPurchaseItemsTreeGrid(Purchase purchase) {
        TreeGrid<PurchaseItem> treeGrid = new TreeGrid<>(PurchaseItem.class);
        HierarchicalDataProvider<PurchaseItem, Void> dataProvider = new AbstractHierarchicalDataProvider<PurchaseItem, Void>() {
            @Override
            public int getChildCount(HierarchicalQuery<PurchaseItem, Void> query) {
                return query.getParent() == null ? purchase.getPurchaseItems().size() : 0;
            }

            @Override
            public Stream<PurchaseItem> fetchChildren(HierarchicalQuery<PurchaseItem, Void> query) {
                return query.getParent() == null ?
                        purchase.getPurchaseItems().stream() :
                        	Collections.<PurchaseItem>emptyList().stream();
            }

            @Override
            public boolean hasChildren(PurchaseItem item) {
                return !item.getPurchaseItems().isEmpty();
            }

			@Override
			public boolean isInMemory() {
				// TODO Auto-generated method stub
				return false;
			}
        };

        treeGrid.setDataProvider(dataProvider);
        treeGrid.setColumns("pr", "purchaseItems", "quantity", "price");

        return treeGrid;
    }


    // Create sample purchase items
    private List<PurchaseItem> createPurchaseItems(int purchaseId) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(new PurchaseItem(1, "Item A", 2, 10.0));
        purchaseItems.add(new PurchaseItem(2, "Item B", 3, 15.0));
        // Add more purchase items as needed
        return purchaseItems;
    }
    
    private List<PurchaseItem> createPurchaseItems2(int purchaseId) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(new PurchaseItem(1, "Item C", 2, 10.0));
        purchaseItems.add(new PurchaseItem(2, "Item D", 3, 15.0));
        // Add more purchase items as needed
        return purchaseItems;
    }
    
    private List<PurchaseItem> createPurchaseItems3(int purchaseId) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(new PurchaseItem(1, "Item E", 2, 10.0));
        purchaseItems.add(new PurchaseItem(2, "Item F", 3, 15.0));
        // Add more purchase items as needed
        return purchaseItems;
    }
    
    private ListDataProvider createDataProvider() {
        return new ListDataProvider(purchases);
    }
    
    private List<Purchase> createSamplePurchases() {
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(1, LocalDate.of(2023, 1, 15), 100.0, "INV-001", createPurchaseItemsNow(1)));
        purchases.add(new Purchase(2, LocalDate.of(2023, 1, 1), 100.0, "INV-002", createPurchaseItems2(2)));
        purchases.add(new Purchase(3, LocalDate.of(2023, 1, 16), 100.0, "INV-003", createPurchaseItems3(3)));
        // Add more purchases as needed
        return purchases;
    }

    private List<PurchaseItem> createPurchaseItemsNow(int purchaseId) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        purchaseItems.add(new PurchaseItem(1, "Item A", 2, 10.0));
        purchaseItems.add(new PurchaseItem(2, "Item B", 3, 15.0));
        // Add more purchase items as needed
        return purchaseItems;
    }

    private Collection<PurchaseItem> getPurchaseItems(Purchase purchase) {
        return purchase.getPurchaseItems();
    }
    // Simple POJO class representing a Purchase
    public static class Purchase {
        private int nr;
        private LocalDate date;
        private double totalPrice;
        private String invoice;
        private Collection<PurchaseItem> purchaseItems;

        public Purchase(int nr, LocalDate date, double totalPrice, String invoice, List<PurchaseItem> purchaseItems) {
            this.nr = nr;
            this.date = date;
            this.totalPrice = totalPrice;
            this.invoice = invoice;
            this.purchaseItems = purchaseItems;
        }

        public int getNr() {
            return nr;
        }

        public LocalDate getDate() {
            return date;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public String getInvoice() {
            return invoice;
        }

        public Collection<PurchaseItem> getPurchaseItems() {
            return purchaseItems;
        }
    }

    // Simple POJO class representing a PurchaseItem
    public static class PurchaseItem {
        private int pr;
        private String purchaseItems;
        private int quantity;
        private double price;

        public PurchaseItem(int pr, String purchaseItems, int quantity, double price) {
            this.pr = pr;
            this.purchaseItems = purchaseItems;
            this.quantity = quantity;
            this.price = price;
        }

        public int getPr() {
            return pr;
        }

        public String getPurchaseItems() {
            return purchaseItems;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }
    }
    
    class ListDataProvider implements HierarchicalDataProvider<Purchase, Void> {

        private static final long serialVersionUID = 1L;
		private final List<Purchase> purchases;

        public ListDataProvider(List<Purchase> purchases) {
            this.purchases = purchases;
        }

        @Override
        public int getChildCount(HierarchicalQuery<Purchase, Void> query) {
            return (int) getChildren(query).count();
        }

        @Override
        public boolean hasChildren(Purchase item) {
            return !item.getPurchaseItems().isEmpty();
        }

        @Override
        public Stream<Purchase> fetchChildren(HierarchicalQuery<Purchase, Void> query) {
            return getChildren(query);
        }

        private Stream<Purchase> getChildren(HierarchicalQuery<Purchase, Void> query) {
            return purchases.stream()
                    .filter(purchase -> Objects.equals(query.getParent(), purchase));
        }

        @Override
        public boolean isInMemory() {
            return true;
        }

		@Override
		public void refreshItem(Purchase item) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void refreshAll() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Registration addDataProviderListener(DataProviderListener<Purchase> listener) {
			// TODO Auto-generated method stub
			return null;
		}
    }
}