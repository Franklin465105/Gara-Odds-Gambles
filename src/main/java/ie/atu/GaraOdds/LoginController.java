package ie.atu.GaraOdds;

import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @GetMapping("/")
    public String loginForm() {
        return """
                <h2>Welcome to GaraOdds</h2>
                <form method='POST' action='/login'>
                    Username: <input type='text' name='username'/><br><br>
                    Password: <input type='password' name='password'/><br><br>
                    <button type='submit'>Login</button>
                </form>
                
                <br><hr><br>
                
                <!-- sign up form -->
                <h3>Sign Up</h3>
                <form method='POST' action='/signup'>
                    Username: <input type='text' name='username'/><br><br>
                    Password: <input type='password' name='password'/><br><br>
                    <button type='submit'>Sign Up</button>
                </form>
                """;
        // Template of the login form for the webaite
    }

    @PostMapping("/login")
    // Handles when the login form is submitted
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        // It reads username and password values from the form

        // checks saved login details
        if (UserStorage.login(username, password)) {

            return "<h1>You are Logged in</h1>" +
                    "<p>Welcome, " + username + ".</p>" +
                    "<a href='/balance?username=" + username + "'>View Balance</a><br><br>" +
                    // logout goes back to home page
                    "<a href='/'>Logout</a>";
        }
        return "<h3>Invalid credentials</h3><a href='/'>Try again</a>";
    }

    // handles sign up form
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password) {

        // stops empty username or password
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return "<h3>Username and password cannot be empty</h3><a href='/'>Back</a>";
        }

        // stops same username being used again
        if (UserStorage.userExists(username)) {
            return "<h3>Username already exists</h3><a href='/'>Back</a>";
        }

        boolean created = UserStorage.signUp(username, password);

        if (created) {
            return "<h2>Account created successfully</h2>" +
                    "<p>Welcome, " + username + ".</p>" +
                    // new user starts with 500
                    "<p>Your starting balance is €500.0</p>" +
                    "<a href='/balance?username=" + username + "'>Go to Balance</a><br><br>" +
                    "<a href='/'>Logout</a>";
        }

        return "<h3>Sign up failed</h3><a href='/'>Back</a>";
    }
}