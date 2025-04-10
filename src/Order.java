import java.util.*;
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
    public static synchronized void loadLastOrderNumber() {
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
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // 1. Display menu
            System.out.println("=== Welcome to Our Bakery ===");
            displayMenu();
            
            // 2. Initialize cart
            Cart cart = new Cart();
            boolean ordering = true;
            
            // 3. Ordering loop
            while (ordering) {
                System.out.print("\nEnter product ID to add to cart (or 'checkout' to finish): ");
                String input = scanner.nextLine().trim();
                
                if (input.equalsIgnoreCase("checkout")) {
                    ordering = false;
                } else {
                    try {
                        System.out.print("Enter quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        
                        cart.addItem(input, quantity);
                        System.out.println("Added to cart!");
                        cart.displayCart();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
            
            // 4. Checkout process
            if (!cart.getItems().isEmpty()) {
                System.out.println("\n=== Checkout ===");
                System.out.print("Enter your phone number: ");
                String phone = scanner.nextLine();
                
                System.out.print("Order type (online/walkin): ");
                String type = scanner.nextLine();
                
                Order order = new Order(phone, type);
                cart.checkout(order);
                
                System.out.println("\nOrder completed! Your order ID is: " + order.getOrderId());
                System.out.println("Thank you for your purchase!");
            } else {
                System.out.println("Your cart is empty. Goodbye!");
            }
            
        } catch (Exception e) {
            System.err.println("System error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    public static void displayMenu() throws IOException {
        System.out.println("\n=== Today's Menu ===");
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Inventory.csv"))) {
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
}
