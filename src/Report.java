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
   
    
    
}

    

    





