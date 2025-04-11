public class OnlineUser extends User {
    // Additional field for online users
    private String uPassword;
    
    // Constructor with fields
    public OnlineUser(String uId, String uName, String uPhone, String uPassword) {
        super(uId, uName, uPhone);
        this.uPassword = uPassword;
    }
    
    // Default constructor
    public OnlineUser() {
        super();
    }
    
    // Additional getter and setter
    public String getUPassword() {
        return uPassword;
    }
    
    public void setPassword(String uPassword) {
        this.uPassword = uPassword;
    }
    
    public void setPassword() {
        System.out.println("Enter your password: ");
        this.uPassword = sc.nextLine();
    }
    
    @Override
    public String getUserType() {
        return "Online";
    }
    
    @Override
    public boolean authenticate(String... credentials) {
        // Online users authenticate with ID and password
        if (credentials.length < 2) return false;
        
        String inputId = credentials[0];
        String inputPassword = credentials[1];
        
        return this.uId.equals(inputId) && this.uPassword.equals(inputPassword);
    }
    
    @Override
    public String toCSVString() {
        // Format: uId,Online,uPassword,uName,uPhone
        return uId + ",Online," + uPassword + "," + uName + "," + uPhone;
    }
    
    @Override
    protected void completeRegistration() {
        // Online users need to set a password
        System.out.print("Create your password: ");
        uPassword = sc.nextLine();
        System.out.println("Online user registration complete!");
    }
}