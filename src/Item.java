import java.util.ArrayList;

public abstract class Item{
	String name;
	int qty;
	String id;
	String type;
	
	public Item(String id, String name, int qty, String type){
		this.id = id;
		this.name = name;
		this.qty = qty;
		this.type = type;
	}
	
	
}