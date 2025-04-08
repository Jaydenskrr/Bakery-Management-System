import java.util.*;
import java.io.*;
import java.time.LocalDateTime; // Get System date and time
import java.time.format.DateTimeFormatter; // Format date and time

public class Order {
	
	private String orderId;
    private String custPhone;
    private LocalDateTime time;
    private double total;
    private String type; // Online or WalkIn
    private static int orderCtr = 1;
    
    private ArrayList<String> productIds;
    private ArrayList<String> quantities;
    
    
    public Order() {
    	
    }
    
    public Order(String custPhone, String type) {
    	orderId = generateOrderId();
        this.custPhone = custPhone;
        time = LocalDateTime.now();
        this.type = type;
        productIds = new ArrayList<>();
        quantities = new ArrayList<>();
        total = 0.0;
    }
}
