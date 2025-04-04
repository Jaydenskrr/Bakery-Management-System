
public class Order {
	protected String iName, iId, iType;
	protected double iPrice;
	protected int iQty;
	
	public Order() {
		
	}
	
	public Order(String iName, String iId, String iType, double iPrice, int iQty) {
		this.iName = iName;
		this.iId = iId;
		this.iType = iType;
		this.iPrice = iPrice;
		this.iQty = iQty;
	}
}
