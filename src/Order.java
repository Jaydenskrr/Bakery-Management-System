//import java.util.*;
import java.io.*;
//import java.time.LocalDateTime; // Get System date and time
//import java.time.format.DateTimeFormatter; // Format date and time

public class Order {
	
	private static int latestOrderId = 0;
	private String orderId;
//    private String custPhone;
//    private LocalDateTime time;
//    private double total;
//    private String type; // Online or WalkIn
    
	//path to csv file
	private static final String path = "src/order.csv";
	
	//Instantiate Buffered REader
	static {
	    loadLastOrderNumber();
	}
    
	// Generates sequential order IDs (ORD-001, ORD-002...)
    public String generateOrderId() {
        latestOrderId++;
        orderId = "ORD-" + String.format("%03d", latestOrderId);
        return orderId;
    }
    
	// Load last used number from orders.csv
    private static synchronized void loadLastOrderNumber() {
        try {
        	BufferedReader or = null;
        	
        	or = new BufferedReader(new FileReader(path));
            String lastLine = "";
            String line;
            
            // Find the last non-empty line
            while ((line = or.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lastLine = line;
                }
            }
            
            if (!lastLine.isEmpty()) {
                // Extract ID from last line
                String lastId = lastLine.split(",")[0];
                latestOrderId = Integer.parseInt(lastId.split("-")[1]);
            }
        } catch (IOException | NumberFormatException e) {
            // If file doesn't exist or is empty, start from 1
        	latestOrderId = 1;
        }
    }
    
    public static void main(String[] args) {
        Order order = new Order();
        System.out.println("First ID: " + order.generateOrderId());
        System.out.println("Second ID: " + order.generateOrderId());
    }

//    public Order() {
//    	
//    }
//    
//    public Order(String custPhone, String type) {
//    	orderId = generateOrderId();
//        this.custPhone = custPhone;
//        time = LocalDateTime.now();
//        this.type = type;
//        productIds = new ArrayList<>();
//        quantities = new ArrayList<>();
//        total = 0.0;
//    }
}
