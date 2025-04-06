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
	
	Scanner sc = new Scanner(System.in);
	
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
		
		System.out.println("Bakery Member or Guest? (m/g)");
		String custType = sc.nextLine();
		
		if (custType.equals("m")) {
			
			System.out.println("LIST OF EXISTING MEMBERS");
			Customer c = new Customer();
			c.displayCustInfo();
			System.out.println("Enter Customer ID (E.g.: 001");
			String cID = sc.nextLine();
			if (cID.equals(c))
			
		} else {
			System.out.println("Continue as guest...");
		}
		
		
		orderID = ("ORD-" + String.format("%03d", orderCtr));
		
		systemDT = LocalDateTime.now();
		
		//Format System Date and Time
	    DateTimeFormatter formatDT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String orderDT = systemDT.format(formatDT);
		
		System.out.println("Order ID: " + orderID);
		System.out.println("Date/Time: " + orderDT);
		System.out.println("Customer Name: ");
		
		//While loop to add item into cart
		
		// ONLY IF ORDER IS SUCCESSFULLY CREATED
		orderCtr++;
		
		// IF GUEST, POINTS = 0
	}
}
