import java.io.*; //BufferedReader, FileReader, IOException
import java.util.*; // ArrayList, Array, List



public class Item {
	
	private String itemId, itemName;
	private int sold;
	private double unitPrice, totalSales;
	
	
	//path to csv file
	String path = "src/Inventory.csv";
	
	//instantiating BufferedReader
	BufferedReader rr = null;
	BufferedReader mr = null;
	
	//to store each line read
	String line = " ";
	
	//ArrayList to store the data
	ArrayList<ArrayList<String>> data = new ArrayList<>();
	
	
	//display method for report
	public void report() throws IOException {
		try {
			//calling BufferedReader rr to read from CSV file
			rr = new BufferedReader(new FileReader(path));
			while((line = rr.readLine()) != null) {
				
				//removing "," from CSV to blank spaces
				String[] value = line.split(",");
				
				//to read the rows as list
				ArrayList<String> row = new ArrayList<>(Arrays.asList(value));
				
				data.add(row);
				
				}
			for(ArrayList<String> row : data) {
				System.out.println(row);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rr.close();
		}
	}
	
	// display method for menu 
	public void menu() throws IOException{
		try {
			
			mr = new BufferedReader(new FileReader(path));
			
			String headerLine = mr.readLine();
			
			if (headerLine == null) return;
			
			
			
			while((line = mr.readLine()) != null) {
				String [] fields = line.split(",");
				
				if (fields.length >= 4) {
					String itemId = fields[0];
					String itemName = fields[1];
					String unitPrice = fields[2];
					
					System.out.println("ID: " +itemId + ", Name: " +itemName + ", Unit Price: " +unitPrice);
				}
				
			}
				
		}catch(Exception e) {
			
		}	
	}
	
	public static void main(String[] args) throws IOException {
		Item item = new Item();
		item.report();
		item.menu();
		}
}

	