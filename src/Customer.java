import java.util.*;

public class Customer 
{
	private String userId, userName;
	private int custNo;
	
	public Customer(String userId, String userName, int  custNo)
	{
		this.userId = userId;
		this.userName = userName;
		this.custNo = custNo;
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
	
	ArrayList<Customer> al = new ArrayList<Customer>();
	
	public void custInfo()
	{
	al.add(new Customer("001", "Jayden", 011111000));
	al.add(new Customer("002", "JJ", 011111100));
	al.add(new Customer("003", "Mateen", 011111110));
	al.add(new Customer("004", "Siew", 011111111));
	}
	
	
	
}