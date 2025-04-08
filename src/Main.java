import java.util.Scanner;

public class Main {

	public static String mainPage() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nWelcome to The Kooks!");
		System.out.println("Please choose the following options: ");
		System.out.println("1. Log in");
		System.out.println("2. Start Order");
		System.out.println("3. Admin");
		
		String opt1 = sc.nextLine(); //Read user input
		
		if(opt1.equals("1") || opt1.equals("2") || opt1.equals("3")) {
			System.out.println("Option chosen: " + opt1);
		} else {
			System.out.println("Please enter 1, 2 or 3.");
			mainPage();
		}
		
		return opt1;
		
	}

	public static void main(String[] args) {
		String selectedOpt = mainPage(); //Display Main page (options)
		
		switch(selectedOpt) {
			case "1":
				System.out.println("Log in");
				
				
				break;
				
			case "2":
				System.out.println("Start Order");
				break;
				
			case "3":
				System.out.println("Admin");
				
		}
		
	}

}
