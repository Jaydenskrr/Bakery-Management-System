import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		mainPage(); //Display Main page (options)
//		option1(); //Get option from user and directs to the page/function
			
	}
	
	public static void mainPage() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nWelcome to The Kooks!");
		System.out.println("Please choose the following options: ");
		System.out.println("1. Order");
		System.out.println("2. Report");
		System.out.println("3. Customer Management");
		System.out.println("Please select the option (1/2/3): ");
		
		String opt1 = sc.nextLine(); //Read user input
		
		if(opt1.equals("1")) {
			System.out.println("Option 1");
		} else if (opt1.equals("2")) {
			System.out.println("Option 2");
		} else if (opt1.equals("3")) {
			System.out.println("Option 3");
		} else {
			System.out.println("Please enter 1, 2 or 3.");
			mainPage();
		}
		
	}
	
}
