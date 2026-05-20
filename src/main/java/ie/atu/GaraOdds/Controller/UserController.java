package ie.atu.GaraOdds.Controller;

import ie.atu.GaraOdds.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // checks if a user exists, called by the betting microservice
    @GetMapping("/exists")
    public ResponseEntity<Boolean> userExists(@RequestParam String username) {
        return ResponseEntity.ok(userService.userExists(username));
    }
}