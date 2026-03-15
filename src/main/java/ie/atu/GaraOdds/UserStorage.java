package ie.atu.GaraOdds;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class UserStorage {

    // stores usernames and passwords
    private static HashMap<String, String> users = new HashMap<>();

    // stores balances
    private static HashMap<String, Double> balances = new HashMap<>();

    // filename
    private static final String FILE_NAME = "users.txt";

    // loads saved users when application start
    static {
        loadUsers();
    }

    // reads users from file
    public static void loadUsers() {
        File file = new File(FILE_NAME);

        // if file does not exist make admin user default one
        if (!file.exists()) {
            users.put("admin", "1234");
            balances.put("admin", 500.0);
            saveUsers();
            return;
        }

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                // file format is username password balance
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);

                    users.put(username, password);
                    balances.put(username, balance);
                }
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // makes sure admin is there
        if (!users.containsKey("admin")) {
            users.put("admin", "1234");
            balances.put("admin", 500.0);
            saveUsers();
        }
    }

    // saves all users to file
    public static void saveUsers() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME));

            for (String username : users.keySet()) {
                String password = users.get(username);
                double balance = balances.get(username);

                out.println(username + "," + password + "," + balance);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Could not write to file: " + e.getMessage());
        }
    }

    // checks login details
    public static boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    // checks if user exists
    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    // adds a new user
    public static boolean signUp(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        // gives new user a starting balance
        users.put(username, password);
        balances.put(username, 500.0);
        saveUsers();
        return true;
    }

    // gets the users balance
    public static double getBalance(String username) {
        if (balances.containsKey(username)) {
            return balances.get(username);
        }
        return 0.0;
    }

    // adds money to balance
    public static boolean deposit(String username, double amount) {
        if (!balances.containsKey(username)) {
            return false;
        }

        double currentBalance = balances.get(username);
        balances.put(username, currentBalance + amount);
        saveUsers();
        return true;
    }

    // takes money from balance
    public static boolean withdraw(String username, double amount) {
        if (!balances.containsKey(username)) {
            return false;
        }

        double currentBalance = balances.get(username);

        if (amount > currentBalance) {
            return false;
        }

        balances.put(username, currentBalance - amount);
        saveUsers();
        return true;
    }
}
