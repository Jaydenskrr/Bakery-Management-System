import java.util.*;

public class Customer 
{
	private String userId, userName, custNo;
	private int points;
	
	public Customer()
	{
		
	}
	
	// defining constructor
	public Customer(String userId, String userName, String custNo, int points)
	{
		
	}
	
	
	public void setCustomer(String userId, String userName, String custNo, int points)
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
	
	
	public String getNo()
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
		cInfo.add(new Customer("000", "Guest", "", 0));
		cInfo.add(new Customer("001", "Jayden", "0119876123", 0));
		cInfo.add(new Customer("002", "JJ", "0119876123", 0));
		cInfo.add(new Customer("003", "Mateen", "0119876123", 0));
		cInfo.add(new Customer("004", "Siew", "0119876123", 0));
	}
	
	
	
	public void displayCustInfo()
	{
		for (int i = 0; i < cInfo.size(); i++)
		{
			System.out.println((cInfo.get(i)).toString());
		}
		
	}	
}