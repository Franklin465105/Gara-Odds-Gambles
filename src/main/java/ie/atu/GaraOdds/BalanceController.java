
package ie.atu.GaraOdds;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
public class BalanceController {

    // User balances
    private HashMap<String, Double> balances = new HashMap<>();

    public BalanceController() {
        balances.put("admin", 500.00); // default user
    }

    @GetMapping("/balance")
    public String showBalance(@RequestParam String username) {

        Double balance = balances.get(username);

        if (balance != null) {
            return "<h2>Welcome, " + username + "!</h2>" +
                    "<p>Your balance is: €" + balance + "</p>" +

                    "<h3>Add Money</h3>" +
                    "<form method='POST' action='/deposit'>" +
                    "<input type='hidden' name='username' value='" + username + "'/>" +
                    "Amount: <input type='number' step='0.01' name='amount'/><br><br>" +
                    "<button type='submit'>Deposit</button>" +
                    "</form><br>" +

                    "<a href='/'>Logout</a>";
        }

        return "<h3>User not found</h3><a href='/'>Back to Login</a>";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String username,
                          @RequestParam double amount) {

        // Check for negative or zero amounts
        if (amount <= 0) {
            return "<h3>Error. Please enter a positive amount.</h3>" +
                    "<a href='/balance?username=" + username + "'>Back to Balance</a>";
        }

        Double currentBalance = balances.get(username);

        if (currentBalance != null) {
            currentBalance += amount;
            balances.put(username, currentBalance);

            return "<h2>Deposit Successful</h2>" +
                    "<p>New Balance: €" + currentBalance + "</p>" +
                    "<a href='/balance?username=" + username + "'>Back to Balance</a>";
        }

        return "<h3>User not found</h3><a href='/'>Back to Login</a>";
    }

}

