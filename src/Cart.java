import java.io.*;
import java.util.*;

public class Cart {
    // Parallel lists for cart items
    private ArrayList<String> itemIds = new ArrayList<>();
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<Double> unitPrices = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();
    
    private static final String path_Inventory = "src/Inventory.csv";

    // Add item to cart
    public void addItem(String itemId, int quantity) throws IOException {
        String[] productData = findProductInInventory(itemId);
        if (productData == null) {
            throw new IllegalArgumentException("Product not found: " + itemId);
        }
        //modify
        
        int stock = Integer.parseInt(productData[2]);
        if (stock < quantity) {
            throw new IllegalStateException("Insufficient stock for " + productData[1]);
        }
        
        itemIds.add(itemId);
        itemNames.add(productData[1]);
        unitPrices.add(Double.parseDouble(productData[4]));
        quantities.add(quantity);
    }

    // Remove item from cart
    public boolean removeItem(String itemId) {
        int index = itemIds.indexOf(itemId);
        if (index >= 0) {
            itemIds.remove(index);
            itemNames.remove(index);
            unitPrices.remove(index);
            quantities.remove(index);
            return true;
        }
        return false;
    }

    // Calculate total
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < itemIds.size(); i++) {
            total += unitPrices.get(i) * quantities.get(i);
        }
        return total;
    }

    // Display cart
    public void displayCart() {
        System.out.println("==== Your Cart ====");
        for (int i = 0; i < itemIds.size(); i++) {
            System.out.printf("%s (ID: %s) x%d @ RM%.2f%n",
                itemNames.get(i),
                itemIds.get(i),
                quantities.get(i),
                unitPrices.get(i));
        }
        System.out.printf("TOTAL: RM%.2f%n", getTotal());
    }

    // Save order to CSV
    public void checkout(Order order) throws IOException {
    	if (itemIds.isEmpty()) {
            throw new IllegalStateException("Cannot checkout empty cart");
        }
    	
        validateStock();
        order.saveOrderToCSV(this); //why this?
        updateInventory();
        clearCart();
    }

    private void saveOrder(String orderId, String customerPhone, String orderType) throws IOException {
        // Save to orders.csv
        try (FileWriter writer = new FileWriter("src/orders.csv", true)) {
            writer.write(String.format("%s,%s,%s,%.2f,%s%n",
                orderId, customerPhone, java.time.LocalDateTime.now(), getTotal(), orderType));
        }
        
        // Save to order_items.csv
        try (FileWriter itemWriter = new FileWriter("src/order_items.csv", true)) {
            for (int i = 0; i < itemIds.size(); i++) {
                itemWriter.write(String.format("%s,%s,%d,%.2f,%.2f%n",
                    orderId,
                    itemIds.get(i),
                    quantities.get(i),
                    unitPrices.get(i),
                    unitPrices.get(i) * quantities.get(i)));
            }
        }
    }

    private void updateInventory() throws IOException {
        ArrayList<String> inventoryLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path_Inventory))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inventoryLines.add(line);
            }
        }

        try (FileWriter writer = new FileWriter(path_Inventory)) {
            writer.write(inventoryLines.get(0) + "\n"); // Write header
            
            for (int i = 1; i < inventoryLines.size(); i++) {
                String[] fields = inventoryLines.get(i).split(",");
                int itemIndex = itemIds.indexOf(fields[0]);
                
                if (itemIndex >= 0) {
                    // Update stock and sold quantities
                    int stock = Integer.parseInt(fields[2]) - quantities.get(itemIndex);
                    int sold = Integer.parseInt(fields[3]) + quantities.get(itemIndex);
                    fields[2] = String.valueOf(stock);
                    fields[3] = String.valueOf(sold);
                    fields[5] = String.format("%.2f", Double.parseDouble(fields[4]) * sold);
                }
                writer.write(String.join(",", fields) + "\n");
            }
        }
    }

    private void clearCart() {
        itemIds.clear();
        itemNames.clear();
        unitPrices.clear();
        quantities.clear();
    }

    private String[] findProductInInventory(String itemId) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path_Inventory))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(itemId)) {
                    return data;
                }
            }
        }
        return null;
    }
    
    private void validateStock() throws IOException {
        for (int i = 0; i < itemIds.size(); i++) {
            String[] product = findProductInInventory(itemIds.get(i));
            int stock = Integer.parseInt(product[2]); // Stock column
            if (stock < quantities.get(i)) {
                throw new IllegalStateException(
                    "Insufficient stock for " + itemNames.get(i) + 
                    " (Available: " + stock + ")"
                );
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Cart cart = new Cart();
            cart.addItem("B003", 2);
            cart.displayCart();
            
            Order order = new Order("+60123456789", "online");
            cart.checkout(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}