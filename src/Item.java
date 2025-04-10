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
	BufferedWriter uw = null;
	Scanner input = new Scanner(System.in);

	
	//to store each line read
	String line = "";
	
	//ArrayList to store the data
	ArrayList<ArrayList<String>> data = new ArrayList<>();
	
	ArrayList<String> copy = new ArrayList<>();
	ArrayList<String> update = new ArrayList<>();
	
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
			
			rr = new BufferedReader(new FileReader(path));
			
			String headerLine = rr.readLine();
			
			if (headerLine == null) return;
			
			
			
			while((line = rr.readLine()) != null) {
				
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
			uw = new BufferedWriter(new FileWriter(path,true));
			uw.write(newData);
			uw.newLine();
			
			
		} catch(Exception e) {
			
		} finally {
			uw.close();
		}
		
	}
	
	boolean itemFound = false;
	
	public void removeItem() throws IOException {
		String delete = "";
		
		System.out.println("Enter item ID you would like to delete: ");
		delete = input.nextLine();
		
//		ArrayList<String> copy = new ArrayList<>();
//		boolean itemFound = false;
		
		try {
			rr = new BufferedReader(new FileReader(path));
			
			String line = "";
			
			String header = rr.readLine();
			if(header != null) {
				copy.add(header);
			}
			
			while((line = rr.readLine()) != null) {
				String[] fields = line.split(",");
			
			
				if(fields.length > 0 && !fields[0].equals(delete)) {
					copy.add(line);
				} else {
					itemFound = true;
				}
			}
		} catch(Exception e) {
			
		} finally {
			rr.close();
		}
		
		if(!itemFound) {
			System.out.println("Item was not found " +delete);
		}
		
		try {
			uw = new BufferedWriter(new FileWriter(path));
			
			for(String row : copy) {
				uw.write(row);
				uw.newLine();
			}
			
		} catch (Exception e) {
			
		} finally {
			uw.close();
		}
	}
	
	public void restock() throws IOException {
		final int ID_COLUMN = 0;   
		final int STOCK_COLUMN = 2; 
		
		String append = "";
		int newQty;
	
		System.out.println("Enter the item ID you would like to append: ");
		append = input.nextLine();
		System.out.println("Enter quantity to be added: ");
		newQty = Integer.parseInt(input.nextLine());
		
		
		try {
			rr = new BufferedReader(new FileReader(path));
			String line = "";
			Boolean found = false;
			
			String header = rr.readLine();
			if(header != null) {
				update.add(header);
			}
			
			while((line = rr.readLine()) != null) {
				String[] fields = line.split(",");
				
				if(fields.length >= 3 && fields[0].equals(append)) {
	                int stock = Integer.parseInt(fields[STOCK_COLUMN]);
					fields[STOCK_COLUMN] = String.valueOf(stock += newQty);
					found = true;
				
				} 
			
				update.add(String.join(",", fields));
			}
			
			if(!found) {
				System.out.println("Item ID was not found: " +append);
			}
			
		} catch (Exception e) {
			
		} finally {
			input.close();
		}
		
		try {
			uw = new BufferedWriter(new FileWriter(path));
			
			for (String update: update) {
				uw.write(update);
				uw.newLine();
			}
			
		} catch(Exception e) {
			
		} finally {
			uw.close();
		}
		
	}
	
	public void editPrice() throws IOException {
		final int ID_COLUMN = 0;   
		final int PRICE_COLUMN = 4; 
		
		String append = "";
		double newPrice;
	
		System.out.println("Enter the item ID you would like to append: ");
		append = input.nextLine();
		System.out.println("Enter the new price: ");
		newPrice = Double.parseDouble(input.nextLine());
		
		
		try {
			rr = new BufferedReader(new FileReader(path));
			String line = "";
			Boolean found = false;
			
			String header = rr.readLine();
			if(header != null) {
				update.add(header);
			}
			
			while((line = rr.readLine()) != null) {
				String[] fields = line.split(",");
				
				if(fields.length >= 3 && fields[0].equals(append)) {
	                double price = Double.parseDouble(fields[PRICE_COLUMN]);
					fields[PRICE_COLUMN] = String.valueOf(newPrice);
					found = true;
				
				} 
			
				update.add(String.join(",", fields));
			}
			
			if(!found) {
				System.out.println("Item ID was not found: " +append);
			}
			
		} catch (Exception e) {
			
		} finally {
			input.close();
		}
		
		try {
			uw = new BufferedWriter(new FileWriter(path));
			
			for (String update: update) {
				uw.write(update);
				uw.newLine();
			}
			
		} catch(Exception e) {
			
		} finally {
			uw.close();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		Item open = new Item();
//		open.addItem();
//		open.removeItem();
		open.editPrice();
		
	}
}

	