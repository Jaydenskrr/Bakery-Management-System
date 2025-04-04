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
	
	
	public String getId(String userId)
	{
		return userId;
	}
	
	public String getName(String userName)
	{
		return userName;
	}
	
	public String getPw(String userPw)
	{
		return userPw;
	}
	
	public int getNo(int custNo)
	{
		return custNo;
	}
	
	ArrayList<Customer> al = new ArrayList<Customer>();
	al.add(new Customer("001", "Jayden", "abc123", 011111000));

}
