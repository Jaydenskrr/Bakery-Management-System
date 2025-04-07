import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime; // Get System date and time
import java.time.format.DateTimeFormatter; // Format date and time

public class Order {
	protected String orderID, custID;
	protected LocalDateTime systemDT;
//	protected double orderPrice;
//	protected int orderQty;
	protected int orderCtr = 1;
	
	//Cart
	protected String itemCode;
	protected int quantity;
	
	Scanner sc = new Scanner(System.in);
	
	public Order() {
		
	}
	
	public Order(String orderID, LocalDateTime systemDT, double orderPrice, int orderQty) {
		this.orderID = orderID;
		this.systemDT = systemDT;
//		this.orderPrice = orderPrice;
//		this.orderQty = orderQty;
	}
	
	
	/* Order Data*/
	ArrayList<Order> orders = new ArrayList<Order>(); // ArrayList to store order history
	ArrayList<Order> cart = new ArrayList<Order>(); // change order data to Cart later
	
	public void createOrder() {
		
		String cID = "";
		
		System.out.println("Bakery Member or Guest? (m/g)");
		String custType = sc.nextLine();
		
		if (custType.equals("m")) { // if customer is member
			
			System.out.println("LIST OF EXISTING MEMBERS");
			Customer c = new Customer();
			c.displayCustInfo();
			System.out.println("Enter Customer ID: ");
			cID = sc.nextLine();
			Customer foundCust = c.findCustByID(cID);
			
			if (foundCust != null) {
				System.out.println("Customer Name: " + foundCust.getName());
			} else {
				System.out.println("Member not found. Continue as guest..."); // if member not found, continue as guest
				cID = "000";
			}
			
		} else { // continue as guest if not member
			System.out.println("Continue as guest...");
			cID = "000";
		}
		
		
		orderID = ("ORD-" + String.format("%03d", orderCtr));
		systemDT = LocalDateTime.now();
		
		//Format System Date and Time
	    DateTimeFormatter formatDT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String orderDT = systemDT.format(formatDT);
		
		System.out.println("Order ID: " + orderID);
		System.out.println("Date/Time: " + orderDT);
		
		//Print menu here
		
		//While loop to add item into cart
		
		
		orderCtr++;
		
		// IF GUEST, set POINTS = 0
	}
	
		//Create method for temp orders (cart)
	public  String addToCart(String itemCode, int quantity) {
        cart.add(new Cart(itemCode, quantity));  // Create CartItem objects
    }
	
	public String checkOut() {
		System.out.println("ORDER SUMMARY"); 
		System.out.println("[Show item in cart]"); 
		
		System.out.println("\nPAYMENT METHOD");
		System.out.println("1. Cash");
		System.out.println("2. Credit Card");
		System.out.println("3. E-wallet (DuitNow)");
		System.out.println("Choose payment method: ");
		
		String paymentChoice = sc.nextLine();
		
		switch(paymentChoice) {
			case "1", "Cash":
				System.out.println("Cash Chosen");
		}
		
	}
}
