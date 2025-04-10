import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AdminUser extends User {
    // Additional field for admin
    private String adminPassword;
    
    // Default admin credentials
    private static final String DEFAULT_ADMIN_ID = "Admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "123";
    
    // Constructor with fields
    public AdminUser(String uId, String adminPassword) {
        super(uId, "Admin", "N/A");
        this.adminPassword = adminPassword;
    }
    
    // Default constructor - creates admin with default credentials
    public AdminUser() {
        super(DEFAULT_ADMIN_ID, "Admin", "N/A");
        this.adminPassword = DEFAULT_ADMIN_PASSWORD;
    }
    
    public String getAdminPassword() {
        return adminPassword;
    }
    
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    
    @Override
    public String getUserType() {
        return "Admin";
    }
    
    @Override
    public boolean authenticate(String... credentials) {
        // Admin authenticates with ID and password
        if (credentials.length < 2) return false;
        
        String inputId = credentials[0];
        String inputPassword = credentials[1];
        
        return this.uId.equals(inputId) && this.adminPassword.equals(inputPassword);
    }
    
    @Override
    public String toCSVString() {
        // Format: Admin,Admin,adminPassword,Admin,N/A
        return uId + ",Admin," + adminPassword + ",Admin,N/A";
    }
    
    @Override
    protected void completeRegistration() {
        // Admin registration would typically be handled differently
        // This method would only be used if creating additional admin accounts
        System.out.print("Create admin password: ");
        adminPassword = sc.nextLine();
        System.out.println("Admin user registration complete!");
    }
    
    // Method to create default admin account if it doesn't exist
    public static void createDefaultAdmin() {
        boolean adminExists = false;
        
        // Check if admin already exists
        for (User user : allUsers) {
            if (user instanceof AdminUser && user.getUId().equals(DEFAULT_ADMIN_ID)) {
                adminExists = true;
                break;
            }
        }
        
        // Create default admin if it doesn't exist
        if (!adminExists) {
            AdminUser admin = new AdminUser();
            allUsers.add(admin);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_USER, true))) {
                writer.write(admin.toCSVString() + "\n");
            } catch (IOException e) {
                System.out.println("Error saving admin data: " + e.getMessage());
            }
            System.out.println("Default admin account created.");
        }
    }
    
    // Override customerMenu to prevent admins from accessing customer functionality
    @Override
    public void customerMenu() {
        System.out.println("Administrators cannot access the customer menu.");
        System.out.println("Please use the admin menu instead.");
        adminMenu();
    }
}