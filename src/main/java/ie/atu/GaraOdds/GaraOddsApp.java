import java.util.HashMap;
import java.util.Scanner;

public class GaraOddsApp {

    // Simple in-memory user storage
    private static HashMap<String, String> users = new HashMap<>();

    public static void main(String[] args) {

        // Pre-register a test user
        users.put("admin", "1234");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to GaraOdds");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (login(username, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials.");
        }
        scanner.close();
    }

    public static boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
