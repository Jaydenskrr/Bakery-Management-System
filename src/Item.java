import java.io.*; //BufferedReader, FileReader, IOException
import java.util.*; // ArrayList, Array, List



public class Item {
	
//	private String itemId, itemName;
//	private int sold;
//	private double unitPrice, totalSales;
	
	
	
	
	//path to csv file
	String path = "src/Inventory.csv";
	
	//instantiating BufferedReader
	BufferedReader rr = null;
	BufferedReader mr = null;
	BufferedWriter nr = null;

	
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
				
				//storing temporary lines into an array value
				//removing "," and replace with " "
				String[] value = line.split(",");
				
				//creating a new ArrayList with values from array value
				ArrayList<String> row = new ArrayList<>(Arrays.asList(value));
				
				//adding data into the ArrayList
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
				
				//fields.length determines the number of desired fields/variables to be printed
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
	
	
	public void newItem(String data) throws IOException {
		try {
			nr = new BufferedWriter(new FileWriter(path,true));
			nr.write(data);
			nr.newLine();
			
		} catch(Exception e) {
			
		} finally {
			nr.close();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		Item item = new Item();
//		item.report();
//		item.menu();
		
		String option = "";
		
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("1. View Menu");
			System.out.println("2. View Report");
			System.out.println("3. Add Items");
			System.out.println("0. Exit");
			option = sc.next();
			
			switch(option) {
				case "1" :
					item.menu();
					break;
				case "2" :
					item.report();
					break;
				case "3":
					item.newItem(option);
					break;
				default: System.out.println("Exited");
			}
			
		} while(!option.equals("0"));
		
		
		}
}

	