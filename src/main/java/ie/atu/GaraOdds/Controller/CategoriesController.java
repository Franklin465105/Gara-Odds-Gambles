package ie.atu.GaraOdds.Controller;

import ie.atu.GaraOdds.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private final UserService userService;

    public CategoriesController(UserService userService) {
        this.userService = userService;
    }

    // shows available categories
    @GetMapping
    public ResponseEntity<?> showCategories(@RequestParam String username) {

        // checks if user exists
        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(
                "Categories:\n" +
                        "Sports\n" +
                        "Esports\n" +
                        "Chancer"

        );
    }

    // shows sports options only
    @GetMapping("/sports")
    public ResponseEntity<?> showSports(@RequestParam String username) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(
                "Sports available: Football, Basketball, Tennis, Golf, MMA"
        );
    }

    // shows esports options only
    @GetMapping("/esports")
    public ResponseEntity<?> showEsports(@RequestParam String username) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(
                "Esports available: COD, League of Legends, Valorant, Fortnite"
        );
    }

    @GetMapping("/chancer")
    public ResponseEntity<?> showChancer(@RequestParam String username) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(
                "Chancers available: numbers,colour,dicer"
        );
    }
}