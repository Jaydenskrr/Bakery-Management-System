import java.util.*;

public class Report extends Item{
	
	public Report(String id, String name, int qty, String type){
		super(id, name, qty, type);
		
	}

	ArrayList<Item> view = new ArrayList<Item>();
	
	public void sales(){
		view.add(new Report("A01", "Bread", 10, "bun"));
	}
	
	
	public void displayReport() {
		sales();
		for (int i = 0; i < view.size(); i++) {
			System.out.println((view.get(i)).toString());
		}
	}
	
	public static void main(String[] args){
		Report r = new Report(null, null, 0, null);
		
		r.displayReport();
	}
	
	
}
