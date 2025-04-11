import java.io.*;
import java.util.*;

public class User {
    Order order = new Order();
    Item item = new Item();

    // Constants and Static Variables
    private static final String PATH_USER = "user.csv"; 
    private static final ArrayList<User> allUsers = new ArrayList<>();
    private final String adminUserName = "Admin";
    private final String adminPassword = "123";

    // User Information
    private String uId;
    private String uType; // "Walk_In", "Online"
    private String uPassword;
    private String uName;
    private String uPhone;

    // Scanner for input
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

    //Setter
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

    // Load Customer Data from CSV
    public static void loadCustomersFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_USER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String uId = data[0];
                    String uType = data[1];
                    String uPassword = data[2];
                    String uName = data[3];
                    String uPhone = data[4];
                    User user = new User(uId, uType, uPassword, uName, uPhone);
                    allUsers.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
    }

    // Save New Customer to CSV
    public void saveCustomerToCSV(User user) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_USER, true))) {
        String customerData = user.getUId() + "," +
                             user.getUType() + "," +
                             user.getUPassword() + "," +
                             user.getUName() + "," +
                             user.getUPhone() + "\n";
        writer.write(customerData);
    } catch (IOException e) {
        System.out.println("Error saving customer data: " + e.getMessage());
    }
    }

    // Check if user ID already exists
    private boolean isUserIdTaken(String userId) {
        for (User user : allUsers) {
            if (user.getUId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    // Handle Customer Registration
    public void customerRegistration() {
        System.out.println("\n=== Customer Registration ===");
        System.out.println("Enter your user type: ");
        System.out.println("1. Walk-In");
        System.out.println("2. Online");
        System.out.println("0. Exit");
    
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        // Exit early if user chooses to cancel
        if (choice == 0) {
            System.out.println("Registration cancelled. Exiting...");
            return;  // Exit the method without creating a user
        }
        
        // Handle invalid choices
        if (choice != 1 && choice != 2) {
            System.out.println("Invalid choice. Going back to main page...");
            return;  // Exit the method without creating a user
        }

        // Get user ID first and check if it's already taken
        System.out.print("Create your ID: ");
        uId = sc.nextLine();
        
        // Check if ID is already taken
        if (isUserIdTaken(uId)) {
            System.out.println("User ID already exists. Please try again with a different ID.");
            return;  // Exit the method without creating a user
        }

        switch(choice){
            case 1:
                uType = "Walk_In";
                System.out.println("=== Please enter your details below ===");
                uPassword = "N/A";  // Set default password for Walk-In users
                System.out.print("Create your name: ");
                uName = sc.nextLine();
                System.out.print("Enter your phone number: ");
                uPhone = sc.nextLine();
                break;
            case 2:
                uType = "Online";
                System.out.println("=== Please enter your details below ===");
                System.out.print("Create your password: ");
                uPassword = sc.nextLine();
                System.out.print("Create your name: ");
                uName = sc.nextLine();
                System.out.print("Enter your phone number: ");
                uPhone = sc.nextLine();
                break;
        }     
      
        // Create and add new user to CSV
        User newUser = new User(uId, uType, uPassword, uName, uPhone);
        allUsers.add(newUser);
        saveCustomerToCSV(newUser);
        System.out.println("Registration successful! Welcome, " + uName + "!");
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

        switch(typeChoice) {
        case 1:
            uType = "Walk_In";
            break;
        case 2:
            uType = "Online";
            break;
        default:
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
            System.out.println("Welcome " + adminUserName + "!");
            loggedIn = true;
            adminMenu();
         } else {
            atm--;
            System.out.println("Invalid username or password. Please try again. You have " + atm + " attempts left.");  
         }
        }
        if (!loggedIn) {
            System.out.println("You have exceeded the maximum number of attempts. Exiting...");
        }
    }

    public void adminMenu(){
        System.out.println("\n=== Welcome to Admin Menu ===");
        System.out.println("1. View Customer list");
        System.out.println("2. View Inventory List");
        System.out.println("3. Generate Sales Report");
        System.out.println("4. Exit");

        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine(); // Clear invalid input
            return;
        }

        switch (choice) {
            case 1:
                System.out.println("\n=== Registered Customer List ===");
                System.out.println("Total customers: " + allUsers.size());
                for (User user : allUsers) {
                    System.out.println("ID: " + user.getUId() + 
                        " | Type: " + user.getUType() + 
                        " | Name: " + user.getUName() + 
                        " | Phone: " + user.getUPhone());
                }
                break;

            case 2:
                System.out.println("\n=== View Inventory List ===");
                try {
                    //item.displayInventory(); // Assuming there's a method in Item class to display inventory
                } catch (Exception e) {
                    System.out.println("Error displaying inventory: " + e.getMessage());
                }
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
    
    public void customerMenu(){
        System.out.println("\n=== Welcome to The Kooks! ===");
        System.out.println("1. Start Order");
        System.out.println("2. Order History");
        System.out.println("0. Exit");

        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine(); // Clear invalid input
            return;
        }

        switch (choice) {
            case 1:
                try {
                    // 1. Display menu
                    System.out.println("=== Welcome to Our Bakery ===");
                    item.menu();
                    
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
                        String phone = this.getUPhone();
                        String type = this.getUType();
                        
                        Order order = new Order(phone, type);
                        cart.checkout(order);
                        
                        System.out.println("\nOrder completed! Your order ID is: " + order.getOrderId());
                        System.out.println("Thank you for your purchase!");
                    } else {
                        System.out.println("Your cart is empty. Goodbye!");
                    }
                    
                } catch (Exception e) {
                    System.err.println("System error: " + e.getMessage());
                } 
                break;

            case 2:
                try {
                    Order.displayCustomerHistory(this.getUPhone());
                } catch (IOException e) {
                    System.out.println("Error accessing order history: " + e.getMessage());
                }
                break;

            case 0:
                System.out.println("Exiting...");
                break;

            default:
                System.out.println("Invalid Choice! Please try again.");
        }
    }
}