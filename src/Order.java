import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*; // Get System date and dt

public class Order {
	
	private static int latestOrderId = 0;
	private String orderId;
    private String custPhone;
    private String type; // Online or WalkIn
    private OrderStatus status;
    
	//paths to csv file
	private static final String path_order = "src/orders.csv";
    private static final String path_orderItem = "src/order_items.csv";
    private static final String path_Inventory = "src/Inventory.csv";
    
	public enum OrderStatus {
        PAYMENT_PENDING, 
        COMPLETED,
        CANCELLED
    }

	static {
	    loadLastOrderNumber();
	}
    
	public Order() {
		orderId = generateOrderId();
        status = OrderStatus.PAYMENT_PENDING;
	}
	
	public Order(String custPhone, String type) {
		this();
	    this.custPhone = custPhone;
	    this.type = type;
	}
	
    //Status
    public void markAsPaid() {
        this.status = OrderStatus.COMPLETED;
    }
    
    // Status check methods
    public boolean isPaymentPending() {
        return status == OrderStatus.PAYMENT_PENDING;
    }
    
    public boolean isCompleted() {
        return status == OrderStatus.COMPLETED;
    }
    
    // Getter
    public OrderStatus getStatus() {
        return status;
    }
	
	// Generates sequential order IDs (ORD-001, ORD-002...)
    public String generateOrderId() {
    	latestOrderId++;
        orderId = "ORD-" + String.format("%03d", latestOrderId);
        return orderId;
    }
    
    public String getOrderId() {
    	return orderId;
    }
    
	// Load last used number from orders.csv
    public static synchronized void loadLastOrderNumber() {
    	File file = new File(path_order);
        if (!file.exists()) {
            latestOrderId = 0;
            return;
        }
        
        try (BufferedReader or = new BufferedReader(new FileReader(file))) {
            String lastLine = "";
            String line;
            
            while ((line = or.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lastLine = line;
                }
            }
            
            if (!lastLine.isEmpty()) {
                String lastId = lastLine.split(",")[0];
                latestOrderId = Integer.parseInt(lastId.split("-")[1]);
            }
        } catch (Exception e) {
            latestOrderId = 0;
        }
    }
    
    public void saveOrderToCSV(Cart cart) throws IOException {
    	try (FileWriter writer = new FileWriter(path_order, true)) {
    		LocalDateTime dt = LocalDateTime.now();
    		dt.toString();
    		DateTimeFormatter dtTemp = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    		String formattedDT = dt.format(dtTemp);
    		
            writer.write(String.join(",",
                orderId,
                custPhone,
                formattedDT,
                String.format("%.2f", cart.getTotal()),
                type
            ) + "\n");
        }
    }
    
    // Helper method to print file contents
    public static void printFileContents(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
    }
    
    public static void displayMenu() throws IOException {
        System.out.println("\n=== Today's Menu ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(path_Inventory))) {
            // Skip header
            reader.readLine();
            
            String line;
            System.out.printf("%-8s %-20s %-10s %-8s\n", 
                "ID", "Item Name", "Price", "Stock");
            System.out.println("----------------------------------------");
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.printf("%-8s %-20s RM%-9.2f %-8s\n",
                    parts[0], parts[1], Double.parseDouble(parts[4]), parts[2]);
            }
        }
    }

    public static ArrayList<String> getCustomerOrders(String phoneNumber) throws IOException {
        ArrayList<String> customerOrders = new ArrayList<>();
        File ordersFile = new File(path_order);
        
        if (!ordersFile.exists()) {
            return customerOrders; // Return empty list if no orders exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFile))) {
            // Skip header if exists
            reader.readLine();
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] columns = line.split(",");
                if (columns.length >= 2 && columns[1].trim().equals(phoneNumber)) {
                    customerOrders.add(line);
                }
            }
        }
        return customerOrders;
    }

    public static void displayCustomerHistory(String phoneNumber) throws IOException {
        List<String> orders = getCustomerOrders(phoneNumber);
        
        if (orders.isEmpty()) {
            System.out.println("No orders found for phone number: " + phoneNumber);
            return;
        }

        System.out.println("\n=== Order History for " + phoneNumber + " ===");
        
        for (String order : orders) {
            String[] orderData = order.split(",");
            System.out.println("\nOrder ID: " + orderData[0]);
            System.out.println("Date: " + orderData[2]);
            System.out.println("Type: " + orderData[4]);
            System.out.println("Total: RM" + orderData[3]);
            
            // Display order items
            System.out.println("Items:");
            displayOrderItems(orderData[0]);
        }
    }

    private static void displayOrderItems(String orderId) throws IOException {
        File itemsFile = new File(path_orderItem);
        if (!itemsFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(itemsFile))) {
            // Skip header if exists
            reader.readLine();
            
            String line;
            boolean foundItems = false;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] columns = line.split(",");
                if (columns[0].equals(orderId)) {
                    System.out.printf("- %s x%s @ RM%s each (Total: RM%s)%n",
                        columns[1], columns[2], columns[3], columns[4]);
                    foundItems = true;
                }
            }
            
            if (!foundItems) {
                System.out.println("(No items found for this order)");
            }
        }
    }


    //  Main method for testing
    // public static void main(String[] args) {
    //         Scanner sc = new Scanner(System.in);
            
    //         try {
    //             // 1. Display menu
    //             System.out.println("=== Welcome to Our Bakery ===");
    //             displayMenu();
                
    //             // 2. Initialize cart
    //             Cart cart = new Cart();
    //             boolean ordering = true;
                
    //             // 3. Ordering loop
    //             while (ordering) {
    //                 System.out.print("\nEnter product ID to add to cart (or 'checkout' to finish): ");
    //                 String input = sc.nextLine().trim();
                    
    //                 if (input.equalsIgnoreCase("checkout")) {
    //                     ordering = false;
    //                 } else {
    //                     try {
    //                         System.out.print("Enter quantity: ");
    //                         int quantity = Integer.parseInt(sc.nextLine());
                            
    //                         cart.addItem(input, quantity);
    //                         System.out.println("Added to cart!");
    //                         cart.displayCart();
    //                     } catch (Exception e) {
    //                         System.out.println("Error: " + e.getMessage());
    //                     }
    //                 }
    //             }
                
    //             // 4. Checkout process
    //             if (!cart.getItems().isEmpty()) {
    //                 System.out.println("\n=== Checkout ===");
    //                 System.out.print("Enter your phone number: ");
    //                 String phone = sc.nextLine();
                    
    //                 System.out.print("Order type (online/walkin): ");
    //                 String type = sc.nextLine();
                    
    //                 Order order = new Order(phone, type);
    //                 cart.checkout(order);
                    
    //                 System.out.println("\nOrder completed! Your order ID is: " + order.getOrderId());
    //                 System.out.println("Thank you for your purchase!");
    //             } else {
    //                 System.out.println("Your cart is empty. Goodbye!");
    //             }
                
    //         } catch (Exception e) {
    //             System.err.println("System error: " + e.getMessage());
    //         } finally {
    //             sc.close();
    //         }
    //     }

}
