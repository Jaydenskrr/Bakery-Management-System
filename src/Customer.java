import java.util.*;

public class Customer 
{
	private String userId, userName, userPw;
	private int custNo;
	
	public Customer(String userId, String userName, String userPw, int  custNo)
	{
		this.userId = userId;
		this.userName = userName;
		this.userPw = userPw;
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
	
	public String getPw()
	{
		return userPw;
	}
	
	public int getNo()
	{
		return custNo;
	}
	
	ArrayList<Customer> al = new ArrayList<Customer>();
	
	public void define
	al.add(new Customer("001", "Jayden", "abc123", 011111000));

}
