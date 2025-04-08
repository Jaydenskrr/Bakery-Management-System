import java.io.*; //BufferedReader, FileReader, IOException
import java.util.*; // ArrayList, Array, List



public class Item {
	
	private String itemId, itemName;
	private int sold;
	private double unitPrice, totalSales;

	
	//path to csv file
	String path = "/Users/waichoong/Bakery-Management-System/src/Inventory.csv";
	
	//instantiating BufferedReader
	BufferedReader br = null;
	
	//to store each line read
	String line = " ";
	
	//ArrayList to store the data
	ArrayList<ArrayList<String>> data = new ArrayList<>();
	
	public void display() throws IOException {
		try {
			br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null) {
				
				String[] value = line.split(",");
				
				ArrayList<String> row = new ArrayList<>(Arrays.asList(value));
				
				data.add(row);
				
				}
			for(ArrayList<String> row : data) {
				System.out.println(row);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
	}
	public static void main(String[] args) throws IOException {
		Item item = new Item();
		item.display();
		}
}

	