import java.util.*;

public class Report 
{

    //Attributes for menu items
    protected String iId;
    protected String iName;
    protected String iType;
    protected double iPrice;
    protected int iQty;

    //Attributes for reports
    // private String reportId;
    // private String dateGenerated;

    public Report(){

    }

    public Report(String iId, String iName, String iType, double iPrice, int iQty){
        this.iId = iId;
        this.iName = iName;
        this.iType = iType;
        this.iPrice = iPrice;
        this.iQty = iQty;
        // this.reportId = reportId;
        // this.dateGenerated = dateGenerated;
    }

    public void setData(String iId, String iName, String iType, double iPrice, int iQty){
        this.iId = iId;
        this.iName = iName;
        this.iType = iType;
        this.iPrice = iPrice;
        this.iQty = iQty;
        // this.reportId = reportId;
        // this.dateGenerated = dateGenerated;

    }

    public String getiName(){
        return iName;
    }
    public String getiId(){
        return iId;
    }
    public String getiType(){
        return iType;
    }
    public double getiPrice(){
        return iPrice;
    }
    public int getiQty(){
        return iQty;
    }

    // Arraylist to store the bakery menu items
    ArrayList<Report> itemList = new ArrayList<>();

    // Method to define and add items to menu
    public void defineItemData(){
        itemList.add(new Report("B01", "Red Bean Bun", "Bun", 5.99, 5));
        itemList.add(new Report("B02", "Chocolate Bun", "Bun", 5.99, 10));

        itemList.add(new Report("201", "Chocolate Chip Cookie", "Cookie", 6.99, 15));
        itemList.add(new Report("202", "Vanilla Cookie", "Cookie", 6.99, 20));

        itemList.add(new Report("301", "Tiramisu Cake", "Cake", 10.99, 21));
        itemList.add(new Report("302", "Cheese Cake", "Cake", 10.99, 22));
    }

    public void printInventory(){
        System.out.println("\n=== Remaining Inventory ===");
        for (Report item : itemList){
            System.out.println("ID: " + item.getiId() + " | Name: " + item.getiName() + " | Type: " + item.getiType() + " | Price: RM" + item.getiPrice() + " | Qty: " + item.getiQty());
        }
    }
    public static void main(String[] args) {
        // Create sample data
        Report report = new Report("null", "null", "null", 0, 0);
        report.defineItemData();

        // Customer C1 = new Customer("001", "Jayden", 1111);
        // Customer C2 = new Customer("002", "Wai Choong", 1112);

        double totalSales = 0.00;

        // Customers place orders
        // jayden.orderItem("B01", 1, report.itemList);
        // aiman.orderItem("301", 2, report.itemList);

        // Generate Daily Sales Report (can include more customers here)
        System.out.println("\n=== Daily Sales Report ===");
        // Sales: Hardcoding sample orders. Must loop through all customer orders.
        System.out.println("Order No. N001 Jayden B01 Red Bean Bun x 1 RM5.99");
        System.out.println("Order No. N002 Aiman 301 Tiramisu Cake x 2 RM21.98");

        // Print remaining inventory
        report.printInventory();
    }
}

    

    





