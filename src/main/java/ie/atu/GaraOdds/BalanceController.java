
package ie.atu.GaraOdds;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
public class BalanceController {

    // Stores user balance
    private HashMap<String, Double> balances = new HashMap<>();

    // default balance of 500
    public BalanceController() {
        balances.put("admin", 500.00); // default user
    }

    // to show users balance and ability to deposti or withdraw
    @GetMapping("/balance")
    public String showBalance(@RequestParam String username) {

        Double balance = balances.get(username);

        if (balance != null) {
            return "<h2>Hello, " + username + ".</h2>" +
                    "<p>You have a current balance of: €" + balance + "</p>" +

                    //deposit
                    "<h3>Deposit Money</h3>" +
                    "<form method='POST' action='/deposit'>" +
                    "<input type='hidden' name='username' value='" + username + "'/>" +
                    "Amount: <input type='number' step='0.01' name='amount'/><br><br>" +
                    "<button type='submit'>Deposit</button>" +
                    "</form><br>" +

                    // Withdraw
                    "<h3>Withdraw Money</h3>" +
                    "<form method='POST' action='/withdraw'>" +
                    "<input type='hidden' name='username' value='" + username + "'/>" +
                    "Amount: <input type='number' step='0.01' name='amount'/><br><br>" +
                    "<button type='submit'>Withdraw</button>" +
                    "</form><br>" +


                    "<a href='/'>Logout</a>";
        }

        // for if username cant be found
        return "<h3>User not found</h3><a href='/'>Back to Login</a>";
    }

    // for deposits
    @PostMapping("/deposit")
    public String deposit(@RequestParam String username,
                          @RequestParam double amount) {

        // to stop negative or 0 deposit
        if (amount <= 0) {
            return "<h3>Error. Please enter a positive amount.</h3>" +
                    "<a href='/balance?username=" + username + "'>Back to Balance</a>";
        }

        Double currentBalance = balances.get(username);

        if (currentBalance != null) {
            currentBalance += amount; // adds money to account
            balances.put(username, currentBalance);

            return "<h2>Deposit Successful</h2>" +
                    "<p>Your updated Balance: €" + currentBalance + "</p>" +
                    "<a href='/balance?username=" + username + "'>Back to Balance</a>";
        }

        return "<h3>User not found</h3><a href='/'>Back to Login</a>";
    }

    // adding in a withdraw now

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String username, @RequestParam double amount) {

        Double currentBalance = balances.get(username);

        if (currentBalance == null) {
            return "<h3>User not found</h3><a href='/'>Back to Login</a>";
        }

        //for negative or 0 withdtraw
        if (amount <= 0) {
            return "<h3>Invalid amount. Please enter a positive number.</h3>" +
                    "<a href='/balance?username=" + username + "'>Back to Balance</a>";
        }

        // cant withdraw more than balance
        if (amount > currentBalance) {
            return "<h3>Insufficient funds.</h3>" +
                    "<a href='/balance?username=" + username + "'>Back to Balance</a>";
        }

        // take withdraw amount from account
        currentBalance -= amount;
        balances.put(username, currentBalance);

        return "<h2>Withdrawal Successful</h2>" +
                "<p>New Balance: €" + currentBalance + "</p>" +
                "<a href='/balance?username=" + username + "'>Back to Balance</a>";
    }

}

