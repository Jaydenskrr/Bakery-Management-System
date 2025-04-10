//import java.util.*;
import java.io.*;
import java.time.LocalDateTime; // Get System date and dt
import java.time.format.DateTimeFormatter; // Format date and dt

public class Order {
	
	private static int latestOrderId = 0;
	private String orderId;
    private String custPhone;
    
//    private double total;
    private String type; // Online or WalkIn
    
	//path to csv file
	private static final String path = "src/orders.csv";
	
	
	
	//Instantiate Buffered REader
	static {
	    loadLastOrderNumber();
	}
    
	public Order() {
		orderId = generateOrderId();
	}
	
	public Order(String custPhone, String type) {
		this();
	    this.custPhone = custPhone;
	    this.type = type;
	    
//	    dt = LocalDateTime.now();
//	    productIds = new ArrayList<>();
//	    quantities = new ArrayList<>();
//	    total = 0.0;
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
    private static synchronized void loadLastOrderNumber() {
    	File file = new File(path);
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
    	try (FileWriter writer = new FileWriter(path, true)) {
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
    
    public static void main(String[] args) {
        try {
            // 1. Initialize components
            System.out.println("=== Initializing Bakery System ===");
            Cart cart = new Cart();
            Order order = new Order("+60123456789", "online");
            
            // 2. Add items to cart
            System.out.println("\n=== Adding Items to Cart ===");
            cart.addItem("B001", 2);  // 2 Baguettes
            cart.addItem("C001", 1);  // 1 Tiramisu
            cart.displayCart();
            
            // 3. Process checkout
            System.out.println("\n=== Processing Checkout ===");
            cart.checkout(order);
            System.out.println("Order completed with ID: " + order.getOrderId());
            
            // 4. Verify files were created
            System.out.println("\n=== Verifying Files ===");
            System.out.println("orders.csv exists: " + new File("src/orders.csv").exists());
            System.out.println("order_items.csv exists: " + new File("src/order_items.csv").exists());
            System.out.println("Inventory updated: " + new File("src/Inventory.csv").exists());
            
            // 5. Display order details from files
            System.out.println("\n=== Order Details ===");
            System.out.println("--- orders.csv ---");
            printFileContents("src/orders.csv");
            
            System.out.println("\n--- order_items.csv ---");
            printFileContents("src/order_items.csv");
            
            System.out.println("\n--- Inventory.csv ---");
            printFileContents("src/Inventory.csv");
            
        } catch (Exception e) {
            System.err.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to print file contents
    private static void printFileContents(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
    }
}
