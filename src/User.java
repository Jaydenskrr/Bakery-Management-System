import java.io.*;
import java.util.*;
public class User {

    Order order = new Order();
    Item item = new Item();

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

    public void customerRegistration() {
        System.out.println("\n=== Customer Registration ===");
        System.out.println("Enter your user type: ");
        System.out.println("1. Walk-In");
        System.out.println("2. Online");
        System.out.println("0. Exit");
    
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch(choice){
            case 1:
                uType = "Walk_In";
                System.out.println("=== Please enter your details below ===");
                System.out.print("Create your ID: ");
                uId = sc.nextLine();
                System.out.print("Create your name: ");
                uName = sc.nextLine();
                System.out.print("Enter your phone number: ");
                uPhone = sc.nextLine();
                break;
            case 2:
                uType = "Online";
                System.out.println("=== Please enter your details below ===");
                System.out.print("Create your ID: ");
                uId = sc.nextLine();
                System.out.print("Create your password: ");
                uPassword = sc.nextLine();
                System.out.print("Create your name: ");
                uName = sc.nextLine();
                System.out.print("Enter your phone number: ");
                uPhone = sc.nextLine();
                break;
            case 0: System.out.println("Registration cancelled. Exiting...");
             break;  
            default:
                System.out.println("Invalid choice. Going back to main page...");
            }     
      

        // Create and add new user
        User newUser = new User(uId, uType, uPassword, uName, uPhone);
        allUsers.add(newUser);
        System.out.println("Registration successful! for " +uName + "!");
    }

    public void customerLogin() {
        int attempts = 3;
        boolean loggedIn = false;

        System.out.println("\n=== Customer Login ===");
        System.out.println("Please enter your type: ");
        System.out.println("1. Walk-In");
        System.out.println("2. Online");
        System.out.print("Choose type: ");
        int typeChoice = sc.nextInt();
        sc.nextLine();

        if (typeChoice == 1) {
        uType = "Walk_In";
        } else if (typeChoice == 2) {
            uType = "Online";
        } else {
            System.out.println("Invalid choice. Returning to main menu...");
            return;
        }

        while (!loggedIn && attempts > 0) {
        System.out.println("\n=== Attempting Login (" + attempts + " left) ===");

        System.out.print("Enter your ID: ");
        String inputId = sc.nextLine();

        if (uType.equals("Walk_In")) {
            System.out.print("Enter your phone number: ");
            String inputPhone = sc.nextLine();

            for (User user : allUsers) {
                if (user.getUId().equals(inputId) &&
                    user.getUPhone().equals(inputPhone) &&
                    user.getUType().equals(uType)) {
                    System.out.println("Login Successful!");
                    System.out.println("Welcome " + user.getUName() + "!");
                    loggedIn = true;
                    customerMenu();
                    return;
                }
            }
        } else { // Online user
            System.out.print("Enter your password: ");
            String inputPass = sc.nextLine();

            for (User user : allUsers) {
                if (user.getUId().equals(inputId) &&
                    user.getUPassword().equals(inputPass) &&
                    user.getUType().equals(uType)) {
                    System.out.println("Login Successful!");
                    System.out.println("Welcome " + user.getUName() + "!");
                    loggedIn = true;
                    customerMenu();
                    return;
                }
            }
        }

        attempts--;
        System.out.println("Invalid credentials. Please try again.");
    }

    if (!loggedIn) {
        System.out.println("You have exceeded the maximum number of attempts. Returning to main menu...");
    }

            // switch(typeChoice){
            //     case 1: 
            //     uType = "Walk_In";
            //     System.out.println("Please enter your ID: ");
            //     sc.nextLine();
            //     String inId = sc.nextLine();
            //     System.out.println("Please enter your phone number: ");
            //     String inPhone = sc.nextLine();
            //     for(User user : allUsers){
            //         if(user.getUId().equals(inId) & user.getUPhone().equals(inPhone) && user.getUType().equals(uType)){
            //             System.out.println("Login Successful!!!");
            //             System.out.println("Welcome " + user.getUName() + "!");
            //             customerMenu();
            //             return;
            //         }
            //     }
            //     System.out.println("Invalid ID or password. Please try again.");
            //     break;
            // case 2: 
            //     uType = "Online";
            //     System.out.println("Please enter your ID: ");
            //     sc.nextLine();
            //     String inId1 = sc.nextLine();
            //     System.out.println("Please enter your password: ");
            //     String inPass1 = sc.nextLine();
            //     for(User user : allUsers){
            //         if(user.getUId().equals(inId1) & user.getUPassword().equals(inPass1) && user.getUType().equals(uType)){
            //             System.out.println("Login Successful!!!");
            //             System.out.println("Welcome " + user.getUName() + "!");
            //             customerMenu();

            //             return;
            //         }
            //     }
            //     System.out.println("Invalid ID or password. Please try again.");
            //     break;
            // default:
            //     System.out.println("Invalid choice. Going back to main page...");
            //     // Main page
        
            // }

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
        System.out.println("4. Exit");

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
                //View Inventory.csv
                //Call method from Item class to do events
                break;

            case 3:
                try {
        			item.report();
    			} catch (IOException e) {
        			System.out.println("Error reading menu file: " + e.getMessage());
    			}
    			break;

            case 4:
                System.out.println("Exiting...");
                // Main Page
                break;

            default:
                System.out.println("Invalid Choice! Please try again.");

        }

    }
    
    //Edited by JJ 9:43pm
    public void customerMenu(){
        System.out.println("\n=== Welcome to The Kooks! ===");
        System.out.println("1. Start Order");
        System.out.println("2. Order History");
        System.out.println("0. Exit");

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

                try {
                    // 1. Display menu
                    System.out.println("=== Welcome to Our Bakery ===");
                    order.displayMenu();
                    
                    // 2. Initialize cart
                    Cart cart = new Cart();
                    boolean ordering = true;
                    
                    // 3. Ordering loop
                    while (ordering) {
                        System.out.print("\nEnter product ID to add to cart (or 'checkout' to finish): ");
                        String input = sc.nextLine().trim();
                        
                        if (input.equalsIgnoreCase("checkout")) {
                            ordering = false;
                        } else {
                            try {
                                System.out.print("Enter quantity: ");
                                int quantity = Integer.parseInt(sc.nextLine());
                                
                                cart.addItem(input, quantity);
                                System.out.println("Added to cart!");
                                cart.displayCart();
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    }
                    
                    // 4. Checkout process
                    if (!cart.getItems().isEmpty()) {
                        System.out.println("\n=== Checkout ===");
                        System.out.print("Enter your phone number: ");
                        String phone = sc.nextLine();
                        
                        System.out.print("Order type (online/walkin): ");
                        String type = sc.nextLine();
                        
                        Order order = new Order(phone, type);
                        cart.checkout(order);
                        
                        System.out.println("\nOrder completed! Your order ID is: " + order.getOrderId());
                        System.out.println("Thank you for your purchase!");
                    } else {
                        System.out.println("Your cart is empty. Goodbye!");
                    }
                    
                } catch (Exception e) {
                    System.err.println("System error: " + e.getMessage());
                } finally {
                    sc.close();
                }
                        break;

            case 2:
                System.out.print("Enter customer phone number: ");
                String phone = sc.nextLine();
                try {
                    Order.displayCustomerHistory(phone);
                } catch (IOException e) {
                    System.out.println("Error accessing order history: " + e.getMessage());
                }
                break;

            case 0:
                System.out.println("Exiting...");
                // Main Page
                break;

            default:
                System.out.println("Invalid Choice! Please try again.");

        }

    }

    static {
        new User("C001", "Walk_In", "123", "Customer1", "1234567890");
        new User("C002", "Walk_In", "123", "Customer2", "9876543210");
        new User("C003", "Online", "1234", "Customer3", "5551112222");
        new User("C004", "Online", "1234", "Customer4", "5553334444");
    }

}


