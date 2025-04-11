public class PaymentCash implements Payment{
	private double amountTendered;
    
    public CashPayment() {
        this.amountTendered = amountTendered;
    }
	
	@Override
	public void payment(double amountTendered) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean processPayment(double amount) {
		if (amountTendered >= amount) {
            double change = amountTendered - amount;
            if (change > 0) {
                System.out.printf("Payment successful. Change: RM%.2f\n", change);
            }
            return true;
        }
        System.out.println("Insufficient cash provided.");
        return false;
	}

	@Override
	public String getPaymentDetails() {
		return String.format("Cash payment: RM%.2f tendered", amountTendered);
	}

}
