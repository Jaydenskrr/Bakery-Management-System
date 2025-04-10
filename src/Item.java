import java.io.*; //BufferedReader, FileReader, IOException
import java.text.DateFormat.Field;
import java.util.*; // ArrayList, Array, List



public class Item {
	
	private String itemId, itemName;
	private int sold, stock;
	private double unitPrice, totalSales;
	
	
	
	
	//path to csv file
	String path = "src/Inventory.csv";
	
	//instantiating BufferedReader and Scanner 
	BufferedReader rr = null;
	BufferedReader mr = null;
	BufferedReader dr = null;
	BufferedWriter ur = null;
	BufferedWriter nr = null;
	Scanner input = new Scanner(System.in);
	Scanner read = new Scanner(System.in);

	
	//to store each line read
	String line = "";
	
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
	
	
	public void addItem() throws IOException {
		
		System.out.println("Enter new item ID:" );
		itemId = input.nextLine();
		System.out.println("Enter new item name: ");
		itemName = input.nextLine();
		System.out.println("Enter stock quantity: ");
		stock = Integer.parseInt(input.nextLine());
		System.out.println("Enter unit price: ");
		unitPrice = Double.parseDouble(input.nextLine());


		String newData = String.format("%s,%s,%d,%.2f,%.2f", itemId, itemName, sold, unitPrice, totalSales);
		
		try {
			nr = new BufferedWriter(new FileWriter(path,true));
			nr.write(newData);
			nr.newLine();
			
			
		} catch(Exception e) {
			
		} finally {
			nr.close();
			input.close();
		}
		
	}
	
	public void removeItem() throws IOException {
		String delete = "";
		
		System.out.println("Enter item ID you would like to delete: ");
		delete = read.nextLine();
		
		ArrayList<String> copy = new ArrayList<>();
		boolean itemFound = false;
		
		try {
			dr = new BufferedReader(new FileReader(path));
			
			String line = "";
			
			String header = dr.readLine();
			if(header != null) {
				copy.add(header);
			}
			
			while((line = dr.readLine()) != null) {
				String[] fields = line.split(",");
			
			
				if(fields.length > 0 && !fields[0].equals(delete)) {
					copy.add(line);
				} else {
					itemFound = true;
				}
			}
		} catch(Exception e) {
			
		} finally {
			dr.close();
		}
		
		if(!itemFound) {
			System.out.println("Item was not found " +delete);
		}
		
		try {
			ur = new BufferedWriter(new FileWriter(path));
			
			for(String row : copy) {
				ur.write(row);
				ur.newLine();
			}
			
		} catch (Exception e) {
			
		} finally {
			ur.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Item open = new Item();
//		open.addItem();
		open.removeItem();
	}
}

	