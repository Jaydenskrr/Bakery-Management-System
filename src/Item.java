import java.util.ArrayList;

public abstract class Item 
{
	
	protected String itemType, itemId, itemName;
	protected int itemQty;
	protected double itemPrice;
	
	public abstract void setItem(String type, String id, String name, int qty, double price);
		
	public String getType()
	{
		return itemType;
	}
	
	public String getId()
	{
		return itemId;
	}
	
	public String getName()
	{
		return itemName;
	}
	
	public int getQty()
	{
		return itemQty;
	}
	
	public double getPrice()
	{
		return itemPrice;
	}
	
	ArrayList<Report> item = new ArrayList<Report>();
	
	public void menu()
	{
		item.add(new Report("Bread", "B001", "Baguette", 10, 11.80));
		item.add(new Report("Bread", "B002", "Focaccia", 20, 12.80));
		item.add(new Report("Bread", "B003", "Mexico Bun", 8, 6.80));
		item.add(new Report("Bread", "B004", "Croissant", 10, 10.80));
		item.add(new Report("Bread", "B005", "Bagel", 5, 5.80));
		item.add(new Report("Cake", "K001", "Black Forest", 10, 18.80));
		item.add(new Report("Cake", "K002", "Tiramisu", 5, 23.80));
		item.add(new Report("Cake", "K003", "Earl Grey", 20, 19.80));
		item.add(new Report("Cookie", "C001", "Brownie", 10, 9.80));
		item.add(new Report("Cookie", "C002", "Choco Chip", 10, 9.80));

	}
	
}
