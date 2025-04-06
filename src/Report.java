import java.util.*;

public class Report extends Item
{
	
	private int noSold;
	private double amtSold;
	
	public Report(String type, String id, String name, int qty, double price, int noSold, double amtSold)
	{
		
	}
	
	public void setReport(String type, String id, String name, int qty, double price, int noSold, double amtSold)
	{
		this.noSold = noSold;
		this.amtSold = amtSold;
	}
	
	public int getNoSold()
	{
		return noSold;
	}
	
	public double getAmtSold()
	{
		return amtSold;
	}

	public Report(String type, String id, String name, int qty, double price)
	{
		
	}
	
	public void setItem(String type, String id, String name, int qty, double price)
	{
		itemType = type;
		itemId = id;
		itemName = name;
		itemQty = qty;
		itemPrice = price;
	}
	
	ArrayList<Report> sales = new ArrayList<Report>();
	
	public void report()
	{
		sales.add(new Report("Bread", "B001", "Baguette", 10, 11.80, 0, 0));
		sales.add(new Report("Bread", "B002", "Focaccia", 20, 12.80, 0, 0));
		sales.add(new Report("Bread", "B003", "Mexico Bun", 8, 6.80, 0, 0));
		sales.add(new Report("Bread", "B004", "Croissant", 10, 10.80, 0, 0));
		sales.add(new Report("Bread", "B005", "Bagel", 5, 5.80, 0, 0));
		sales.add(new Report("Cake", "K001", "Black Forest", 10, 18.80, 0, 0));
		sales.add(new Report("Cake", "K002", "Tiramisu", 5, 23.80, 0, 0));
		sales.add(new Report("Cake", "K003", "Earl Grey", 20, 19.80, 0, 0));
		sales.add(new Report("Cookie", "C001", "Brownie", 10, 9.80, 0, 0));
		sales.add(new Report("Cookie", "C002", "Choco Chip", 10, 9.80, 0, 0));

	}
	
	
	
}

    

    





