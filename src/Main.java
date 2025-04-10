import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static String mainPage() {
        System.out.println("\nWelcome to The Kooks!");
        System.out.println("Please choose the following options: ");
        System.out.println("1. Customer Log In");
        System.out.println("2. Admin Log In");
        System.out.println("3. Customer Registration");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        
        String opt1 = sc.nextLine(); //Read user input
        
        while (!opt1.matches("[1-4]")) {
            System.out.println("Invalid input! Please enter 1, 2, 3, 4");
            opt1 = sc.nextLine();
        }
        return opt1;
    }

    public static void main(String[] args) {
        // Initialize classes for the system
        User.loadCustomersFromCSV(); // Load all users from CSV
        AdminUser.createDefaultAdmin(); // Ensure default admin exists
        
        String selectedOpt; 
        
        do {
            selectedOpt = mainPage();
            
            switch(selectedOpt) {
                case "1":
                    System.out.println("Customer Log in");
                    User.userLogin(); // Static method to handle login
                    break;
                    
                case "2":
                    System.out.println("Admin Login");
                    // Navigate directly to admin login section of userLogin
                    User.userLogin();
                    break;
                    
                case "3":
                    System.out.println("Customer Registration");
                    handleRegistration();
                    break;
                    
                case "4":
                    System.out.println("Thank you for visiting The Kooks! Goodbye.");
                    break;
                    
                default:
                    System.out.println("Invalid Choice! Please try again.");
            }

            if (!selectedOpt.equals("4")) {
                System.out.println("\nPress Enter to return to main menu...");
                sc.nextLine();
            }
            
        } while (!selectedOpt.equals("4"));
        
        sc.close();
    }
    
    // Method to handle user registration based on type
    private static void handleRegistration() {
        System.out.println("\n=== User Registration ===");
        System.out.println("Select user type:");
        System.out.println("1. Walk-In Customer");
        System.out.println("2. Online Customer");
        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");
        
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Returning to main menu.");
            return;
        }
        
        User newUser = null;
        
        switch (choice) {
            case 0:
                System.out.println("Registration cancelled.");
                return;
                
            case 1:
                newUser = new WalkInUser();
                break;
                
            case 2:
                newUser = new OnlineUser();
                break;
                
            default:
                System.out.println("Invalid choice! Registration cancelled.");
                return;
        }
        
        // Call the registration method on the new user object
        if (newUser != null) {
            newUser.userRegistration();
        }
    }
}