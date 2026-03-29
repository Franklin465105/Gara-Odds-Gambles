package ie.atu.GaraOdds.Service;

import ie.atu.GaraOdds.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    // checks login details
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // checks if user exists
    public boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // creates a new user with a starting balance of 500
    public boolean signUp(User newUser) {
        if (userExists(newUser.getUsername())) {
            return false;
        }
        newUser.setBalance(500.0);
        users.add(newUser);
        return true;
    }

    // gets the users balance
    public double getBalance(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getBalance();
            }
        }
        return 0.0;
    }

    // adds money to balance
    public boolean deposit(String username, double amount) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setBalance(user.getBalance() + amount);
                return true;
            }
        }
        return false;
    }

    // takes money from balance
    public boolean withdraw(String username, double amount) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (amount > user.getBalance()) {
                    return false;
                }
                user.setBalance(user.getBalance() - amount);
                return true;
            }
        }
        return false;
    }
}