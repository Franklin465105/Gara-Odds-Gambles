
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
        return (balance != null) ?
                "<h2>Welcome, " + username + "!</h2>" +
                        "<p>Your balance is: €" + balance + "</p>" +
                        "<a href='/'>Logout</a>"
                :
                "<h3>User not found</h3><a href='/'>Back to Login</a>";
    }
}

