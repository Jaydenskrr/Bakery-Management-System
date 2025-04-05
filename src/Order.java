import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner;
import java.time.LocalDateTime; // Import the LocalDateTime class, 
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

/* Before Formatting: 2025-04-04T16:08:20.597585
After Formatting: 04-04-2025 16:08:20 */

public class Order {
	protected String orderID, orderTime;
	protected double orderPrice;
	protected int orderIdCtr, orderQty;
	
	//add item to menu
	
	public Order() {
		
	}
	
	public Order(String orderID, String orderTime, double orderPrice, int orderQty) {
		this.orderID = orderID;
		this.orderTime = orderTime;
		this.orderPrice = orderPrice;
		this.orderQty = orderQty;
	}
	
	
	/* Order */
	ArrayList<Order> orders = new ArrayList<Order>(); // Create an ArrayList object
	
	public void createOrder() {
		//While loop to add item into cart
		
	}
}
