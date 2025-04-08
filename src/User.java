import java.util.*;

public class User {

    // Static list to hold all users in memory
    private static final ArrayList<User> allUsers = new ArrayList<>();

    // Admin credentials
    private final String AdminUserName = "Admin";
    private final String AdminPassword = "123";
    private String uId;
    private String uType; // "Walk_In", "Online", "Admin"
    private String uPassword;
    private String uName;
    private String uPhone;


    Scanner sc = new Scanner(System.in);
    
    // Constructor for only Customer
    public User(String uId, String uType, String uPassword, String uName, String uPhone) {
        this.uId = uId;
        this.uType = uType; 
        this.uPassword = uPassword;
        this.uName = uName;
        this.uPhone = uPhone;
    }

    public User() {
    }
    

    public void setUtype(String uType) {
        System.out.println("Enter your user type: ");
        System.out.println("1. Walk-In");
        System.out.println("2. Online");
        System.out.println("0. Exit");
        int choice = sc.nextInt();
        switch(choice){
            case 1:
                uType = "Walk_In";
                System.out.println("Please enter your details: ");
                setUid();
                setName();
                setPhone();
                break;
            case 2:
                uType = "Online";
                System.out.println("Please enter your details: ");
                setUid();
                setPassword();
                setName();
                setPhone();
                break;
            case 3:
                uType = "Admin";

   
    }
    }

    public void setUid() {
        System.out.print("Enter your ID: ");
        uId = sc.nextLine();
     }

    public void setPassword(){
        System.out.println("Enter your password: ");
        uPassword = sc.nextLine();

     }

    public void setName(){
    System.out.println("Enter your name: ");
        uName = sc.nextLine();
    }

    public void setPhone(){
        System.out.println("Enter your phone number: ");
        uPhone = sc.nextLine();
    }
        
    

    public void storeData() {
        for(User user : allUsers){
        allUsers.add((user));
        }
    }

    // Getter
    public String getUId(){
        return uId;
    }
    public String getUType() {  
        return uType;
    }
    public String getUPassword() {
        return uPassword;
    }
    public String getUName() {
        return uName;
    }
    public String getUPhone() {
        return uPhone;
    }

    // To check the User type
    public boolean isWalkIn(){
        return "Walk_In".equalsIgnoreCase(uType);
    }

    public boolean isOnline(){
        return "Online".equalsIgnoreCase(uType);
    }

    // Authentication methods
    // Admin login (username + password)
    
    // Online customer login (phone + password)
    
    
    public boolean authenticationOnline(String phone, String password) {
        return this.isOnline() && this.getUPhone().equals(phone) && this.getUPassword().equals(password);

        }

    public boolean authenticationWalkIn(String phone) {
        return this.isWalkIn() && this.uPhone.equals(phone);
    }

    public boolean authenticationAdmin(String username, String password) {
        return this

    public static User loginOnline(String phone, String password){
        for(User user : allUsers){
            if(user.authenticationOnline(phone, password)){
                return user;
            }
        }
        return null;
    }

    public static User loginWalkIn(String phone){
        for(User user : allUsers){
            if(user.authenticationWalkIn(phone)){
                return user;
            }
        }
        return null;
    }


    static {
        new User("C001", "Walk_In", "1234", "Customer1", "1234567890");
        new User("C002", "Walk_In", "1234", "Customer2", "9876543210");
        new User("C003", "Online", "1234", "Customer3", "1234567890");
        new User("C004", "Online", "1234", "Customer4", "9876543210");
    }







}

