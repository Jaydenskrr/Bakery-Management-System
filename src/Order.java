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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = orderDate.format(formatter);
            
            writer.write(String.join(",",
                orderId,
                custPhone,
                formattedDate,
                String.format("%.2f", totalAmount),
                type,
                status.toString(),
                paymentStatus.toString()  // Add payment status
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

    private static void displayOrderItems(String orderId) throws IOException {
        File itemsFile = new File(path_orderItem);
        if (!itemsFile.exists()) {
            System.out.println("(No items found)");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(itemsFile))) {
            // Skip header
            reader.readLine();
            
            boolean foundItems = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] columns = line.split(",");
                if (columns.length >= 5 && columns[0].equals(orderId)) {
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
    
    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED
    }
    
    private PaymentStatus paymentStatus;
    
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    
    public boolean processPayment(double amount, String paymentType, Scanner scanner) {
        System.out.println("\n=== Payment Processing ===");
        System.out.printf("Total Amount: RM%.2f\n", amount);
        
        if (paymentType.equalsIgnoreCase("cash")) {
            return processCashPayment(amount, scanner);
        } else if (paymentType.equalsIgnoreCase("card")) {
            return processCardPayment(amount, scanner);
        } else {
            System.out.println("Invalid payment method");
            return false;
        }
    }
    
    private boolean processCashPayment(double amount, Scanner scanner) {
        System.out.print("Enter cash amount tendered: RM");
        double cash = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        if (cash >= amount) {
            double change = cash - amount;
            if (change > 0) {
                System.out.printf("Change: RM%.2f\n", change);
            }
            paymentStatus = PaymentStatus.COMPLETED;
            System.out.println("Cash payment received. Thank you!");
            return true;
        } else {
            System.out.println("Insufficient cash provided.");
            paymentStatus = PaymentStatus.FAILED;
            return false;
        }
    }
    
    private boolean processCardPayment(double amount, Scanner scanner) {
        System.out.println("Processing credit card payment...");
        System.out.print("Enter card number (16 digits): ");
        String cardNumber = scanner.nextLine();
        
        System.out.print("Enter expiry date (MM/YY): ");
        String expiry = scanner.nextLine();
        
        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();
        
        // Simple validation
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            System.out.println("Invalid card number");
            this.paymentStatus = PaymentStatus.FAILED;
            return false;
        }
        
        // Simulate payment processing
        System.out.printf("Charging RM%.2f to card ending with %s\n", 
                        amount, cardNumber.substring(12));
        System.out.println("Payment approved!");
        
        paymentStatus = PaymentStatus.COMPLETED;
        return true;
    }
    
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Item item = new Item();
//        
//        try {
//            System.out.println("=== Bakery System Test ===");
//            
//            // 1. Display menu
//            System.out.println("\n--- Current Menu ---");
//            item.menu();
//            
//            // 2. Create test orders for same customer
//            String testPhone = "0123456789";
//            
//            // First order
//            Order order1 = new Order(testPhone, "online");
//            Cart cart1 = new Cart();
//            cart1.addItem("B001", 1); // Baguette
//            cart1.addItem("C002", 3); // Tiramisu
//            order1.completeOrder();
//            cart1.checkout(order1);
//            
//            // Verify order items were saved
//            System.out.println("\n--- Order Items for ORD-" + (latestOrderId) + " ---");
//            Order.displayOrderItems("ORD-" + String.format("%03d", latestOrderId));
//            
//            // 3. Display all order history
//            System.out.println("\n=== Complete Order History ===");
//            Order.displayCustomerHistory(testPhone);
//            
//        } catch (Exception e) {
//            System.err.println("Test failed: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            scanner.close();
//            System.out.println("\n=== Test Completed ===");
//        }
//    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // 1. Create a test order
            System.out.println("=== Bakery Order Test ===");
            Order testOrder = new Order("0123456789", "online");
            
            // 2. Create a test cart with items
            Cart testCart = new Cart();
            testCart.addItem("B001", 1); // Baguette
            testCart.addItem("C001", 1); // Tiramisu
            
            // 3. Display cart contents
            System.out.println("\n=== Order Summary ===");
            testCart.displayCart();
            double total = testCart.getTotal();
            
            // 4. Process payment
            System.out.println("\n=== Payment Options ===");
            System.out.println("1. Cash");
            System.out.println("2. Credit Card");
            System.out.print("Select payment method (1-2): ");
            int paymentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            boolean paymentSuccess;
            if (paymentChoice == 1) {
                paymentSuccess = testOrder.processPayment(total, "cash", scanner);
            } else if (paymentChoice == 2) {
                paymentSuccess = testOrder.processPayment(total, "card", scanner);
            } else {
                System.out.println("Invalid choice");
                return;
            }
            
            // 5. Complete order if payment succeeded
            if (paymentSuccess) {
                testOrder.completeOrder();
                testCart.checkout(testOrder);
                System.out.println("\n=== Order Details ===");
                System.out.println("Order ID: " + testOrder.getOrderId());
                System.out.println("Status: " + testOrder.getStatus());
                System.out.println("Payment Status: " + testOrder.getPaymentStatus());
                
                // Display order history
                System.out.println("\n=== Order History ===");
                Order.displayCustomerHistory("0123456789");
            } else {
                System.out.println("Order not completed due to payment failure");
            }
            
        } catch (Exception e) {
            System.err.println("Error during order processing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\n=== Test Completed ===");
        }
    }
}
