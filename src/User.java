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
        String inName = sc.nextLine();
        System.out.println("Please enter your password: ");
        String inPass = sc.nextLine();
        
        if (inName.equals(adminUserName) && inPass.equals(adminPassword)){
            System.out.println("Welcome" + adminUserName + "!");
            loggedIn = true;
            adminMenu();
         } else {
            atm--;
            System.out.println("Invalid username or password. Please try again. You have " + atm + " attempts left.");  
         }
        }
        if (!loggedIn) {
            System.out.println("You have exceeded the maximum number of attempts. Exiting...");
            // Back to Main page (Loop)
        }
    }

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
                System.out.println("=== Please enter your details below ===");
                setUid();
                sc.nextLine(); // Consume newline
                setName();
                setPhone();
                break;
            case 2:
                uType = "Online";
                System.out.println("=== Please enter your details below ===");
                setUid();
                sc.nextLine(); // Consume newline
                setPassword();
                setName();
                setPhone();
                break;
            case 0: System.out.println("Registration cancelled. Exiting...");
             break;  
            default:
                System.out.println("Invalid choice. Going back to main page...");
            }     
      


        allUsers.add(new User(uId, uType, uPassword, uName, uPhone));
        System.out.println("Registration successful!");
        
    }

    public void customerLogin() {
        System.out.println("\n=== Customer Login ===");
        System.out.println("Please enter your type: ");
        System.out.println("1. Walk-In");
        System.out.println("2. Online");
        System.out.print("Choose type: ");
        int typeChoice = sc.nextInt();
            switch(typeChoice){
                case 1: 
                uType = "Walk_In";
                System.out.println("Please enter your ID: ");
                sc.nextLine();
                String inId = sc.nextLine();
                System.out.println("Please enter your phone number: ");
                String inPhone = sc.nextLine();
                for(User user : allUsers){
                    if(user.getUId().equals(inId) & user.getUPhone().equals(inPhone) && user.getUType().equals(uType)){
                        System.out.println("Login Successful!!!");
                        System.out.println("Welcome " + user.getUName() + "!");
                        return;
                    }
                }
                System.out.println("Invalid ID or password. Please try again.");
                break;
            case 2: 
                uType = "Online";
                System.out.println("Please enter your ID: ");
                sc.nextLine();
                String inId1 = sc.nextLine();
                System.out.println("Please enter your password: ");
                String inPass1 = sc.nextLine();
                for(User user : allUsers){
                    if(user.getUId().equals(inId1) & user.getUPassword().equals(inPass1) && user.getUType().equals(uType)){
                        System.out.println("Login Successful!!!");
                        System.out.println("Welcome " + user.getUName() + "!");
                        return;
                    }
                }
                System.out.println("Invalid ID or password. Please try again.");
                break;
            default:
                System.out.println("Invalid choice. Going back to main page...");
                // Main page
        
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

    public void adminMenu(){
        System.out.println("\n=== Welcome to Admin Menu ===");
        System.out.println("1. View Customer list");
        System.out.println("2. View Inventory List");
        System.out.println("3. Generate Sales Report");
        System.out.println("4. Generate Inventory Report");
        System.out.println("5. Exit");

        int choice = sc.nextInt();
        try {
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine(); // Clear invalid input
        }

        switch (choice) {
            case 1:
                // Customer List (Arraylist)
                break;
            case 2:
                //Inventory List
                break;
            case 3:
                // Generate Sales report method
                break;
            case 4:
                // Generate Inventory report method
                break;
            case 5:
                System.out.println("Existing...");
                // Main Page
                break;
            default:
            System.out.println("Invalid Choice! Please try again.");

        }

    }

    static {
        new User("C001", "Walk_In", "", "Customer1", "1234567890");
        new User("C002", "Walk_In", "", "Customer2", "9876543210");
        new User("C003", "Online", "1234", "Customer3", "5551112222");
        new User("C004", "Online", "1234", "Customer4", "5553334444");
    }

}


