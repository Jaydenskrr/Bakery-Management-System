import java.io.*;
import java.util.*;

public abstract class User {
    // Constants and Static Variables
    protected static final String PATH_USER = "user.csv"; 
    protected static final ArrayList<User> allUsers = new ArrayList<>();
    
    // Scanner for input
    protected Scanner sc = new Scanner(System.in);
    
    // Common User Information
    protected String uId;
    protected String uName;
    protected String uPhone;
    
    // References to other classes
    protected Order order = new Order();
    protected Item item = new Item();
    
    // Default constructor
    public User() {
    }
    
    // Constructor with common fields
    public User(String uId, String uName, String uPhone) {
        this.uId = uId;
        this.uName = uName;
        this.uPhone = uPhone;
    }
    
    // Common setters
    public void setUid() {
        System.out.print("Enter your ID: ");
        uId = sc.nextLine();
    }
    
    public void setName() {
        System.out.println("Enter your name: ");
        uName = sc.nextLine();
    }
    
    public void setPhone() {
        System.out.println("Enter your phone number: ");
        uPhone = sc.nextLine();
    }
    
    // Common getters
    public String getUId() {
        return uId;
    }
    
    public String getUName() {
        return uName;
    }
    
    public String getUPhone() {
        return uPhone;
    }
    
    // Abstract methods that subclasses must implement
    public abstract String getUserType();
    public abstract boolean authenticate(String... credentials);
    
    // Method to convert user data to CSV format
    public abstract String toCSVString();
    
    // Static method to create appropriate user type
    public static User createUser(String uId, String uType, String uPassword, String uName, String uPhone) {
        if (uType.equals("Walk_In")) {
            return new WalkInUser(uId, uName, uPhone);
        } else if (uType.equals("Online")) {
            return new OnlineUser(uId, uName, uPhone, uPassword);
        } else if (uType.equals("Admin")) {
            return new AdminUser(uId, uPassword);
        }
        return null;
    }
    
    // Load all users from CSV
    public static void loadCustomersFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_USER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String uId = data[0];
                    String uType = data[1];
                    String uPassword = data[2];
                    String uName = data[3];
                    String uPhone = data[4];
                    
                    User user = createUser(uId, uType, uPassword, uName, uPhone);
                    if (user != null) {
                        allUsers.add(user);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
    }
    
    // Save new user to CSV
    public void saveCustomerToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_USER, true))) {
            writer.write(this.toCSVString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving customer data: " + e.getMessage());
        }
    }
    
    // Check if user ID already exists
    protected static boolean isUserIdTaken(String userId) {
        for (User user : allUsers) {
            if (user.getUId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
    
    // Registration method - common for all users
    public void userRegistration() {
        System.out.println("\n=== User Registration ===");
        
        // Get user ID first and check if it's already taken
        System.out.print("Create your ID: ");
        uId = sc.nextLine();
        
        // Check if ID is already taken
        if (isUserIdTaken(uId)) {
            System.out.println("User ID already exists. Please try again with a different ID.");
            return;
        }
        
        // Set common fields
        System.out.print("Create your name: ");
        uName = sc.nextLine();
        System.out.print("Enter your phone number: ");
        uPhone = sc.nextLine();
        
        // Each subclass will complete its own registration by overriding completeRegistration()
        completeRegistration();
        
        // Add to allUsers list and save to CSV
        allUsers.add(this);
        saveCustomerToCSV();
        System.out.println("Registration successful! Welcome, " + uName + "!");
    }
    
    // Abstract method to be implemented by subclasses to complete registration
    protected abstract void completeRegistration();
    
    // Main login method
    public static void userLogin() {
        Scanner sc = new Scanner(System.in);
        int attempts = 3;
        boolean loggedIn = false;
        
        System.out.println("\n=== Customer Login ===");
        System.out.println("Please enter your type: ");
        System.out.println("1. Walk-In");
        System.out.println("2. Online");
        System.out.print("Enter Choice: ");
        
        int typeChoice;
        try {
            typeChoice = sc.nextInt();
            sc.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine(); // Clear invalid input
            return;
        }
        
        String userType;
        switch(typeChoice) {
            case 1:
                userType = "Walk_In";
                break;
            case 2:
                userType = "Online";
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu...");
                return;
        }
        
        while (!loggedIn && attempts > 0) {
            System.out.println("\n=== Attempting Login (" + attempts + " left) ===");
            
            System.out.print("Enter your ID: ");
            String inputId = sc.nextLine();
            
            // Different login process based on user type
            if (userType.equals("Walk_In")) {
                System.out.print("Enter your phone number: ");
                String inputPhone = sc.nextLine();
                
                for (User user : allUsers) {
                    if (user instanceof WalkInUser && 
                        user.authenticate(inputId, inputPhone)) {
                        System.out.println("Login Successful!");
                        System.out.println("Welcome " + user.getUName() + "!");
                        loggedIn = true;
                        user.customerMenu();
                        return;
                    }
                }
            } else if (userType.equals("Online")) {
                System.out.print("Enter your password: ");
                String inputPass = sc.nextLine();
                
                for (User user : allUsers) {
                    if (user instanceof OnlineUser && 
                        user.authenticate(inputId, inputPass)) {
                        System.out.println("Login Successful!");
                        System.out.println("Welcome " + user.getUName() + "!");
                        loggedIn = true;
                        user.customerMenu();
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
    
    // Customer menu
    public void customerMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            System.out.println("\n=== Welcome to The Kooks! ===");
            System.out.println("1. Start Order");
            System.out.println("2. Order History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // Clear invalid input
                continue;
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
                            String type = this.getUserType();
                            
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
                    inMenu = false;
                    break;
                
                default:
                    System.out.println("Invalid Choice! Please try again.");
            }
        }
    }
    
    // Admin menu - only for Admin users
    public void adminMenu() {
        boolean adminInMenu = true;
        
        while (adminInMenu) {
            System.out.println("\n=== Welcome to Admin Menu ===");
            System.out.println("1. View Customer list");
            System.out.println("2. Inventory Management");
            System.out.println("3. Generate Sales Report");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // Clear invalid input
                continue;
            }
            
            switch (choice) {
                case 1:
                    System.out.println("\n=== Registered Customer List ===");
                    System.out.println("Total customers: " + (allUsers.size() - 1)); // Exclude admin
                    for (User user : allUsers) {
                        if (!(user instanceof AdminUser)) {
                            System.out.println("ID: " + user.getUId() + 
                                " | Type: " + user.getUserType() + 
                                " | Name: " + user.getUName() + 
                                " | Phone: " + user.getUPhone());
                        }
                    }
                    System.out.println("\nPress Enter to continue...");
                    sc.nextLine();
                    break;
                
                case 2:
                    handleInventoryManagement();
                    break;
                
                case 3:
                    try {
                        item.report();
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } catch (IOException e) {
                        System.out.println("Error reading menu file: " + e.getMessage());
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    }
                    break;
                
                case 4:
                    System.out.println("Exiting to main menu...");
                    adminInMenu = false;
                    break;
                
                default:
                    System.out.println("Invalid Choice! Please try again.");
                    System.out.println("\nPress Enter to continue...");
                    sc.nextLine();
            }
        }
    }
    
    // Inventory management helper method
    private void handleInventoryManagement() {
        boolean inventoryMenu = true;
        
        while (inventoryMenu) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. View Inventory List");
            System.out.println("2. Add New Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Restock Item");
            System.out.println("5. Edit Item Price");
            System.out.println("0. Return to Admin Menu");
            System.out.print("Enter your choice: ");
            
            int invChoice;
            try {
                invChoice = sc.nextInt();
                sc.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // Clear invalid input
                continue;
            }
            
            if (invChoice == 0) {
                System.out.println("Returning to Admin Menu...");
                return; // Exit the method entirely
            }
            
            switch (invChoice) {
                case 1:
                    try {
                        item.report();
                    } catch (IOException e) {
                        System.out.println("Error displaying inventory: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        item.addItem();
                        System.out.println("Item added successfully!");
                    } catch (IOException e) {
                        System.out.println("Error adding item: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        item.removeItem();
                        System.out.println("Item removed successfully!");
                    } catch (IOException e) {
                        System.out.println("Error removing item: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        item.restock();
                        System.out.println("Item restocked successfully!");
                    } catch (IOException e) {
                        System.out.println("Error restocking item: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        item.editPrice();
                        System.out.println("Price updated successfully!");
                    } catch (IOException e) {
                        System.out.println("Error updating price: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            // Only prompt to continue if we're staying in the inventory menu
            System.out.println("\nPress Enter to continue...");
            sc.nextLine();
        }
    }
}