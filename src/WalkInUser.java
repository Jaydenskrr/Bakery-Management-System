public class WalkInUser extends User {
    // Constructor with fields
    public WalkInUser(String uId, String uName, String uPhone) {
        super(uId, uName, uPhone);
    }
    
    // Default constructor
    public WalkInUser() {
        super();
    }
    
    @Override
    public String getUserType() {
        return "Walk_In";
    }
    
    @Override
    public boolean authenticate(String... credentials) {
        // Walk-in users authenticate with ID and phone number
        if (credentials.length < 2) return false;
        
        String inputId = credentials[0];
        String inputPhone = credentials[1];
        
        return this.uId.equals(inputId) && this.uPhone.equals(inputPhone);
    }
    
    @Override
    public String toCSVString() {
        // Format: uId,Walk_In,N/A,uName,uPhone
        return uId + ",Walk_In,N/A," + uName + "," + uPhone;
    }
    
    @Override
    protected void completeRegistration() {
        // Walk-in users don't need additional data during registration
        // Password is set to N/A for walk-in users
        System.out.println("Walk-in user registration complete!");
    }
}