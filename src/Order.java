import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime; // Get System date and time
import java.time.format.DateTimeFormatter; // Format date and time

public class Order {
	protected String orderID, custID;
	protected LocalDateTime systemDT;
	protected double orderPrice;
	protected int orderQty;
	protected int orderCtr = 1;
	protected boolean flag = true;
	
	public Order() {
		
	}
	
	public Order(String orderID, LocalDateTime systemDT, double orderPrice, int orderQty) {
		this.orderID = orderID;
		this.systemDT = systemDT;
		this.orderPrice = orderPrice;
		this.orderQty = orderQty;
	}
	
	
	/* Order */
	ArrayList<Order> orders = new ArrayList<Order>(); // Create an ArrayList object
	
	public void createOrder() {
		
		orderID = ("ORD-" + String.format("%03d", orderCtr));
		orderCtr++;
		systemDT = LocalDateTime.now();
		
		//Format System Date and Time
	    DateTimeFormatter formatDT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String orderDT = systemDT.format(formatDT);
		
		System.out.println("Order ID: " + orderID);
		System.out.println("Date/Time: " + orderDT);
		System.out.println("Customer Name: ");
		
		//While loop to add item into cart
		
	}
}
