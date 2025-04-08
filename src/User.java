import java.util.*;

public class User {

    // Static list to hold all users in memory
    private static final ArrayList<User> allUsers = new ArrayList<>();

    // Admin credentials
    private final String adminUserName = "Admin";
    private final String adminPassword = "123";
    private String uId;
    private String uType; // "Walk_In", "Online"
    private String uPassword;
    private String uName;
    private String uPhone;


    Scanner sc = new Scanner(System.in);
    Scanner sc1 = new Scanner (System.in);
    
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


    public void adminLogin() {
        int atm = 3;
        boolean loggedIn = false;

        while (!loggedIn && atm > 0) {
        System.out.println("\n=== Admin Login ===");
        System.out.println("Please enter your username: ");
        String inName = sc1.nextLine();
        System.out.println("Please enter your password: ");
        String inPass = sc1.nextLine();
        
        if (inName.equals(adminUserName) && inPass.equals(adminPassword)){
            System.out.println("Welcome" + adminUserName + "!");
            loggedIn = true;
            // adminMenu();
         } else {
            atm--;
            System.out.println("Invalid username or password. Please try again. You have " + atm + " attempts left.");  
         }
        }
        if (!loggedIn) {
            System.out.println("You have exceeded the maximum number of attempts. Exiting...");
        }
    }

    // sop = admin or cusotomer switch case admin (call admin class)
    public void customerRegistration() {
        System.out.println("\n=== Customer Registration ===");
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
                sc.nextLine(); // Consume newline
                setName();
                setPhone();
                break;
            case 2:
                uType = "Online";
                System.out.println("Please enter your details: ");
                setUid();
                sc.nextLine(); // Consume newline
                setPassword();
                setName();
                setPhone();
                break;
            case 0: System.out.println("Registration cancelled. Exiting...");
             break;  
            default:
                System.out.println("Invalid choice. Exiting...");
            }     
      


        allUsers.add(new User(uId, uType, uPassword, uName, uPhone));
        System.out.println("Registration successful!");
    }

    // public void customerRegistration() {  // Removed uType parameter
    //     System.out.println("\n=== Customer Registration ===");
    //     System.out.println("1. Walk-In");
    //     System.out.println("2. Online");
    //     System.out.println("0. Exit");
    //     System.out.print("Choose type: ");
    //     int choice = sc.nextInt();
    //     sc.nextLine(); // Consume newline
        
    //     if (choice == 0) {
    //         System.out.println("Registration cancelled.");
    //         return;
    //     }
        
    //     // Common fields
    //     setUid();
    //     setName();
    //     setPhone();
        
    //     // Type-specific fields
    //     if (choice == 1) {
    //         uType = "Walk_In";
    //         uPassword = ""; // No password for walk-in
    //     } else if (choice == 2) {
    //         uType = "Online";
    //         setPassword();
    //     } else {
    //         System.out.println("Invalid choice!");
    //         return;
    //     }
        
    //     // Create and store the new user
    //     allUsers.add(new User(uId, uType, uPassword, uName, uPhone));
    //     System.out.println("Registration successful!");
    // }

    // public void adminMenu() {

    // }



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

    static {
        new User("C001", "Walk_In", "", "Customer1", "1234567890");
        new User("C002", "Walk_In", "", "Customer2", "9876543210");
        new User("C003", "Online", "1234", "Customer3", "5551112222");
        new User("C004", "Online", "1234", "Customer4", "5553334444");
    }

}


