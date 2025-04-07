import java.util.*;

public class Report {
	String itemName;
	String itemId;
	String itemType;
	int itemQty;
	int sold;
	double itemPrice;
	double total;
	
	
	public Report(String itemId, String itemName, String itemType, int itemQty, int sold, double itemPrice, double total) {
		
	}
	
	ArrayList<Report> obj = new ArrayList<>();
	
	public void salesReport() {
		obj.add(new Report("B001", "Baguette", "Bread", 10, 2, 9.90, 19.80));
	}
	
	
	public static void main(String[] args) {
		
	}
	
}
