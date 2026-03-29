package ie.atu.GaraOdds.Controller;

import ie.atu.GaraOdds.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final UserService userService;

    public BalanceController(UserService userService) {
        this.userService = userService;
    }

    // shows the users current balance
    @GetMapping
    public ResponseEntity<?> showBalance(@RequestParam String username) {

        // checks if user exists
        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok("Balance for " + username + ": €" + userService.getBalance(username));
    }

    // adds money to the users balance
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam String username, @RequestParam double amount) {

        // stops negative or 0 deposit
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Please enter a positive amount");
        }

        userService.deposit(username, amount);
        return ResponseEntity.ok("Deposit successful. New balance: €" + userService.getBalance(username));
    }

    // takes money from the users balance
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam String username, @RequestParam double amount) {

        // checks if user exists
        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // stops negative or 0 withdraw
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Please enter a positive amount");
        }

        // cant withdraw more than balance
        if (amount > userService.getBalance(username)) {
            return ResponseEntity.badRequest().body("Insufficient funds");
        }

        userService.withdraw(username, amount);
        return ResponseEntity.ok("Withdrawal successful. New balance: €" + userService.getBalance(username));
    }
}