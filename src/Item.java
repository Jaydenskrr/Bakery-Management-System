
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
	
}
