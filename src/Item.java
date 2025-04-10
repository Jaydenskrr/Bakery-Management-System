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
	
	ArrayList<String> copy = new ArrayList<>();
	ArrayList<String> update = new ArrayList<>();
	
	//display method for report
	public void report() throws IOException {
		System.out.println("\n=== Kooks Sales Report ===");
		try {
			//calling BufferedReader rr to read from CSV file
			rr = new BufferedReader(new FileReader(path));
			
			rr.readLine();
			  System.out.printf("%-8s %-20s %-12s %-10s %-10s %-15s%n", 
			            "ID", "Item Name", "Price", "Stock", "Sold", "Total Sales");
			        System.out.println("-------------------------------------------------------------------------------");

		    String line;
            while ((line = rr.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    System.out.printf("%-8s %-20s %-12s %-10s %-10s %-15s\n", parts[0], parts[1], Double.parseDouble(parts[4]), parts[2], parts[3].isEmpty() ? "0" : parts[3],  parts[5].isEmpty() ? 0 : Double.parseDouble(parts[5]));
				}
				
			}
				
		}catch(Exception e) {
			
		}	
	}
	
	// display method for menu 
	public void menu() throws IOException{
		System.out.println("\n========== Kooks Menu ============");
		try {
			
			rr = new BufferedReader(new FileReader(path));
			
			rr.readLine();
			 System.out.printf("%-8s %-20s %-12s %-8s\n", "ID", "Item Name", "Price", "Stock");
		        System.out.println("-----------------------------------------------");
			
			
		    
            String line;
            while ((line = rr.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    System.out.printf("%-8s %-20s RM%-9.2f %-8s\n", parts[0], parts[1], Double.parseDouble(parts[4]), parts[2]);
				}
				
			}
				
		} catch(Exception e) {
			
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
		open.report();
		open.menu();
	}
}

	