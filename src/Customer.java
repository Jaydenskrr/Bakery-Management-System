import java.util.*;

public class Customer 
{
	private String userId, userName;
	private int custNo, points;
	
	// defining constructor
	public Customer(String userId, String userName, int  custNo, int points)
	{
		
	}
	
	
	public void setCustomer(String userId, String userName, int  custNo, int points)
	{
		this.userId = userId;
		this.userName = userName;
		this.custNo = custNo;
		this.points = points;
	}
	
	public String getId()
	{
		return userId;
	}
	
	public String getName()
	{
		return userName;
	}
	
	
	public int getNo()
	{
		return custNo;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	ArrayList<Customer> cInfo = new ArrayList<Customer>();
	
	public void custInfo()
	{
	cInfo.add(new Customer("001", "Jayden", 011111000, 0));
	cInfo.add(new Customer("002", "JJ", 011111100, 0));
	cInfo.add(new Customer("003", "Mateen", 011111110, 0));
	cInfo.add(new Customer("004", "Siew", 011111111, 0));
	cInfo.add(new Customer("000", "Guest", 0, 0));
	}
	
	public void displayCustInfo()
	{
		for (int i = 0; i < cInfo.size(); i++)
		{
			System.out.println(i+1 + ". " + cInfo.get(i) );
		}
		
	}
	
	
	
	
}