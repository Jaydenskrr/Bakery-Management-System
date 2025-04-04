import java.util.*;

public class Report 
{
    protected String iId;
    protected String iName;
    protected String iType;
    protected double iPrice;
    protected int iQty;

    private String reportId;
    private String dateGenerated;

    public Report(){

    }

    public Report(String iId, String iName, String iType, double iPrice, int iQty, String reportId, String dateGenerated) {
        this.iId = iId;
        this.iName = iName;
        this.iType = iType;
        this.iPrice = iPrice;
        this.iQty = iQty;
        this.reportId = reportId;
        this.dateGenerated = dateGenerated;
    }

    public void setData(String iId, String iName, String iType, double iPrice, int iQty, String reportId, String dateGenerated){
        this.iId = iId;
        this.iName = iName;
        this.iType = iType;
        this.iPrice = iPrice;
        this.iQty = iQty;
        this.reportId = reportId;
        this.dateGenerated = dateGenerated;

    }

    public String iName(){
        return iName;
    }
    public String iId(){
        return iId;
    }
    public String iType(){
        return iType;
    }
    public double iPrice(){
        return iPrice;
    }
    public int iQty(){
        return iQty;
    }
    public String reportId(){
        return reportId;
    }
    public String dateGenerated(){
        return dateGenerated;
    }


    // Arraylist to store the bakery menu items
    ArrayList<Report> itemList = new ArrayList<>();

    // Method to define and add items to menu
    public void defineItemData(){
        itemList.add(new Report("1001", "Red Bean Bun", "Bun", 5.99, 20, "R001", "2025-04-04"));
        itemList.add(new Report("1001", "Chocolate Bun", "Bun", 5.99, 20, "R001", "2025-04-04"));
        itemList.add(new Report("1001", "Butter Bun", "Bun", 5.99, 20, "R001", "2025-04-04"));

        itemList.add(new Report("1001", "Chocolate Chip Cookie", "Cookie", 5.99, 20, "R001", "2025-04-04"));
        itemList.add(new Report("1001", "Vanilla Cookie", "Bun", 5.99, 20, "R001", "2025-04-04"));
        itemList.add(new Report("1001", "Strawberrie Cookie", "Bun", 5.99, 20, "R001", "2025-04-04"));

        itemList.add(new Report("1001", "Vanilla Cookie", "Bun", 5.99, 20, "R001", "2025-04-04"));
        itemList.add(new Report("1001", "Vanilla Cookie", "Bun", 5.99, 20, "R001", "2025-04-04"));
        itemList.add(new Report("1001", "Vanilla Cookie", "Bun", 5.99, 20, "R001", "2025-04-04")); 
    }




}
