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
            Cart cart = new Cart();
            cart.addItem("B001", 2);
            
            Order order = new Order("+60123456789", "online");
            order.saveOrderToCSV(cart);
            System.out.println("Order created: " + order.orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
