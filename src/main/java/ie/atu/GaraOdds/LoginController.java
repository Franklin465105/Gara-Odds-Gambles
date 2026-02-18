package ie.atu.GaraOdds;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class LoginController {

    private HashMap<String, String> users = new HashMap<>();

    public LoginController() {
        users.put("admin", "1234");
        // adds a default user with username  and password
    }
    @GetMapping("/")
    public String loginForm() {
        return """
                <h2>Welcome to GaraOdds</h2>
                <form method='POST' action='/login'>
                    Username: <input type='text' name='username'/><br><br>
                    Password: <input type='password' name='password'/><br><br>
                    <button type='submit'>Login</button>
                </form>
                """;
        // Template of the login form for the webaite
    }
    @PostMapping("/login")
    // Handles when the login form is submitted
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        // It reads username and password values from the form

        if (users.containsKey(username) &&
                users.get(username).equals(password)) {

            return "<h1>You are Logged in</h1>";
        }
        return "<h3>Invalid credentials</h3><a href='/'>Try again</a>";
    }
}

