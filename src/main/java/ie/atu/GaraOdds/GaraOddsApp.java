package ie.atu.GaraOdds;

import java.util.HashMap;
import java.util.Scanner;

public class GaraOddsApp { // main class

    // user Memory Storage
    private static HashMap<String, String> users = new HashMap<>();

    public static void main(String[] args) {

        // user login details
        users.put("admin", "1234");
        // this is the default user and it can be changed.

        Scanner scanner = new Scanner(System.in);
        // reads input from the keyboards

        System.out.println("Welcome to GaraOdds");
        System.out.print("Username: ");
        // asks user to input a name
        String username = scanner.nextLine();
        // reads the username
        System.out.print("Password: ");
        // asks user to input a password
        String password = scanner.nextLine();
        // reads the password
        if (login(username, password)) {
            // checks if details are correct
            System.out.println("Login successful!");
            // displays success message if it is
        } else {
            System.out.println("Invalid credentials.");
            // displays error message if it isnt
        }
        scanner.close();
    }

    public static boolean login(String username, String password) {
        // checks if the username and password match the stored ones
        return users.containsKey(username) && users.get(username).equals(password);
        // returns true if it matches and if not then false
    }
}
