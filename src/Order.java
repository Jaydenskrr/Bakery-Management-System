import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
	
	private static int latestOrderId = 0;
	private String orderId;
    private String custPhone;
    private String type; // Online or WalkIn
    private OrderStatus status;
    private LocalDateTime orderDate;
    
	//paths to csv file
	private static final String path_order = "src/orders.csv";
    private static final String path_orderItem = "src/order_items.csv";
    private static final String path_Inventory = "src/Inventory.csv";
    
    public enum OrderStatus {
        PAYMENT_PENDING("Payment Pending"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");
        
        private final String displayName;
        
        OrderStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }

	static {
	    loadLastOrderNumber();
	}
    
	public Order() {
		orderId = generateOrderId();
        status = OrderStatus.PAYMENT_PENDING;
        orderDate = LocalDateTime.now();
	}
	
	public Order(String custPhone, String type) {
		this();
	    this.custPhone = custPhone;
	    this.type = type;
	}
	
    //Status management
	public void completeOrder() {
        this.status = OrderStatus.COMPLETED;
    }
    
    public void cancelOrder() {
        this.status = OrderStatus.CANCELLED;
    }
    
    public boolean isPaymentPending() {
        return status == OrderStatus.PAYMENT_PENDING;
    }
    
    public boolean isCompleted() {
        return status == OrderStatus.COMPLETED;
    }
    
    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }
    
    // Getter
    public String getOrderId() { return orderId; }
    public String getCustPhone() { return custPhone; }
    public String getType() { return type; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }

	
    /* METHODS */
    
    public String generateOrderId() {
    	latestOrderId++;
        orderId = "ORD-" + String.format("%03d", latestOrderId);
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
    
    public void saveOrderToCSV(double totalAmount) throws IOException {
    	try (FileWriter writer = new FileWriter(path_order, true)) {
    		Cart cart = new Cart();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = orderDate.format(formatter);
            
            writer.write(String.join(",",
                orderId,
                custPhone,
                formattedDate,
                String.format("%.2f", cart.getTotal()),
                type,
                status.toString()
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
            
            System.out.printf("%-8s %-20s %-10s %-8s\n", 
                "ID", "Item Name", "Price", "Stock");
            System.out.println("----------------------------------------");
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    System.out.printf("%-8s %-20s RM%-9.2f %-8s\n",
                        parts[0], parts[1], 
                        Double.parseDouble(parts[4]), parts[2]);
                }
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
            // Skip header
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
            System.out.println("Status: " + orderData[5]);
            System.out.println("Total: RM" + orderData[3]);
            
            System.out.println("Items:");
            displayOrderItems(orderData[0]);
        }
    }

 
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("=== Bakery System Test ===");
            
            // 1. Display menu
            System.out.println("\n--- Current Menu ---");
            Order.displayMenu();
            
            // 2. Create test orders for same customer
            String testPhone = "0123456789";
            List<Order> testOrders = new ArrayList<>();
            
            // First order
            Order order1 = new Order(testPhone, "online");
            Cart cart1 = new Cart();
            cart1.addItem("B001", 1); // Baguette
            cart1.addItem("C001", 1); // Tiramisu
            order1.completeOrder();
            cart1.checkout(order1);
            testOrders.add(order1);
            
            // Second order
            Order order2 = new Order(testPhone, "walkin");
            Cart cart2 = new Cart();
            cart2.addItem("K002", 2); // Choco Chip Cookies
            cart2.addItem("B002", 3); // Garlic Bread
            order2.completeOrder();
            cart2.checkout(order2);
            testOrders.add(order2);
            
            // 3. Display all order history for this customer
            System.out.println("\n=== Complete Order History for " + testPhone + " ===");
            
            // Get all orders from file
            List<String> customerOrders = Order.getCustomerOrders(testPhone);
            if (customerOrders.isEmpty()) {
                System.out.println("No orders found for this customer");
                return;
            }
            
            double totalSpent = 0;
            int orderCount = 0;
            
            // Print each order with details
            for (String orderData : customerOrders) {
                String[] fields = orderData.split(",");
                String orderId = fields[0];
                String date = fields[2];
                String type = fields[4];
                String status = fields[5];
                double total = Double.parseDouble(fields[3]);
                
                totalSpent += total;
                orderCount++;
                
                System.out.printf("\nOrder #%d: %s | %s | %s | %s | RM%.2f%n",
                    orderCount, orderId, date, type, status, total);
                
                System.out.println("Items:");
                Order.displayOrderItems(orderId);
            }
            
            // Print summary
            System.out.printf("\n=== Summary ===%n");
            System.out.printf("Total Orders: %d%n", orderCount);
            System.out.printf("Total Spent: RM%.2f%n", totalSpent);
            
            // 4. Verify files
            System.out.println("\n--- Verifying Files ---");
            System.out.println("Orders file has " + countLines(Order.path_order) + " records");
            System.out.println("Order items file has " + countLines(Order.path_orderItem) + " records");
            
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\n=== Test Completed ===");
        }
    }
    
    private static int countLines(String filePath) throws IOException {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) lines++;
        }
        return lines;
    }
}
