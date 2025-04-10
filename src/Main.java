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
            System.out.println("Invalid input! Please enter 1, 2, 3, 4 or 5");
            opt1 = sc.nextLine();
        }
        return opt1;
    }


	public static void main(String[] args) {
		User user = new User();
		Order order = new Order();
		String selectedOpt; 
		User.loadCustomersFromCSV(); // Load customers when the program starts
		
		do {
            selectedOpt = mainPage();
            
            switch(selectedOpt) {
                case "1":
    			System.out.println("Customer Log in");
    			user.customerLogin();
    			break;
                    
                case "2":
                    System.out.println("Admin Login");
                    user.adminLogin();
                    break;
                    
                case "3":
                    System.out.println("Customer Registration");
					user.customerRegistration();
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

}
