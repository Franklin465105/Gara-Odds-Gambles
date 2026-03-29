package ie.atu.GaraOdds.Controller;

import ie.atu.GaraOdds.Model.User;
import ie.atu.GaraOdds.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handles login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user) {

        // checks if details are correct
        if (!userService.login(user.getUsername(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        return ResponseEntity.ok("Welcome, " + user.getUsername() + "! Login successful.");
    }

    // handles sign up
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody User user) {

        // stops same username being used again
        if (userService.userExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // creates the account with a starting balance of 500
        userService.signUp(user);
        return ResponseEntity.ok("Account created for " + user.getUsername() + ". Starting balance: €500.0");
    }
}