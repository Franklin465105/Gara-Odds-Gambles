package ie.atu.GaraOdds.Controller;

import ie.atu.GaraOdds.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sports")
public class SportsController {

    private final UserService userService;

    public SportsController(UserService userService) {
        this.userService = userService;
    }

    // shows available sports categories
    @GetMapping
    public ResponseEntity<?> showSports(@RequestParam String username) {

        // checks if user exists
        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok("Available sports: Football, Basketball, Rugby, Tennis");
    }
}