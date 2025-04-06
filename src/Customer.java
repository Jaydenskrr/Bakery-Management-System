import java.util.*;

public class Customer 
{
	private String userId, userName, custNo, searchID;
	private int points;
	
	public Customer()
	{
		
	}
	
	// defining constructor
	public Customer(String userId, String userName, String custNo, int points)
	{
		this.userId = userId;
		this.userName = userName;
		this.custNo = custNo;
		this.points = points;
	}
	
	/* Setter and Getter */
	public void setCustomer(String userId, String userName, String custNo, int points)
	{
		this.userId = userId;
		this.userName = userName;
		this.custNo = custNo;
		this.points = points;
	}
	
	public String getId()
	{	return userId;	}
	
	public String getName()
	{	return userName;}
	
	public String getNo()
	{	return custNo;	}
	
	public int getPoints()
	{	return points;	}
	
	/* Customer Information */
	ArrayList<Customer> cInfo = new ArrayList<Customer>();
	
	public void custInfo()
	{
		cInfo.add(new Customer("000", "Guest", "N/A", 0));
		cInfo.add(new Customer("001", "Jayden", "0119876123", 0));
		cInfo.add(new Customer("002", "JJ", "0119876123", 0));
		cInfo.add(new Customer("003", "Mateen", "0119876123", 0));
		cInfo.add(new Customer("004", "Siew", "0119876123", 0));
	}
	
	public String toString() {
		return String.format("%-3s | %-10s | %-12s | %-4d", userId, userName, custNo, points);
	}
	
	public void displayCustInfo() {
		custInfo();
		for (int i = 0; i < cInfo.size(); i++) {
			System.out.println((cInfo.get(i)).toString());
		}
		System.out.println("---End of list---");
	}
	
	public Customer findCustByID(String searchID) {
		
		for(int i = 0; i < cInfo.size(); i++) {
			Customer eCustomer = cInfo.get(i);
			if (eCustomer.getId().equals(searchID)) { //if the existing customer match custId
	            return eCustomer; //return customer information
			}
		}
		
		return null;//return null if not found in list
	}
	

}