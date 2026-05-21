package ie.atu.GaraOdds.Controller;

import ie.atu.GaraOdds.Client.GaraOddsBettingClient;
import ie.atu.GaraOdds.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private final UserService userService;
    private final GaraOddsBettingClient bettingClient;

    private static final Map<String, Double> SPORTS_ODDS = Map.of(
            "Football", 1.8,
            "Basketball", 2.1,
            "Tennis", 1.5,
            "Golf", 3.5,
            "MMA", 2.8
    );

    private static final Map<String, Double> ESPORTS_ODDS = Map.of(
            "COD", 1.9,
            "League of Legends", 2.2,
            "Valorant", 1.7,
            "Fortnite", 3.0
    );

    private static final Map<String, Double> CHANCER_ODDS = Map.of(
            "Numbers", 5.0,
            "Colour", 2.0,
            "Dicer", 6.0
    );

    public CategoriesController(UserService userService, GaraOddsBettingClient bettingClient) {
        this.userService = userService;
        this.bettingClient = bettingClient;
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

        StringBuilder sb = new StringBuilder("Sports available:\n");
        SPORTS_ODDS.forEach((sport, odds) ->
                sb.append("  ").append(sport).append(" — Odds: x").append(odds).append("\n")
        );
        sb.append("\nTo bet: POST /categories/sports/bet?username=&sport=&amount=");
        return ResponseEntity.ok(sb.toString());
    }

    // shows esports options only
    @GetMapping("/esports")
    public ResponseEntity<?> showEsports(@RequestParam String username) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        StringBuilder sb = new StringBuilder("Esports available:\n");
        ESPORTS_ODDS.forEach((game, odds) ->
                sb.append("  ").append(game).append(" — Odds: x").append(odds).append("\n")
        );
        sb.append("\nTo bet: POST /categories/esports/bet?username=&game=&amount=");
        return ResponseEntity.ok(sb.toString());
    }

    @GetMapping("/chancer")
    public ResponseEntity<?> showChancer(@RequestParam String username) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        StringBuilder sb = new StringBuilder("Chancer games available:\n");
        CHANCER_ODDS.forEach((game, odds) ->
                sb.append("  ").append(game).append(" — Odds: x").append(odds).append("\n")
        );
        sb.append("\nTo bet: POST /categories/chancer/bet?username=&game=&amount=");
        return ResponseEntity.ok(sb.toString());
    }

    // places a sports bet
    @PostMapping("/sports/bet")
    public ResponseEntity<?> placeSportsBet(
            @RequestParam String username,
            @RequestParam String sport,
            @RequestParam double amount) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Double odds = SPORTS_ODDS.get(sport);
        if (odds == null) {
            return ResponseEntity.badRequest().body("Sport not found. Available: " + SPORTS_ODDS.keySet());
        }

        try {
            return bettingClient.placeBet(username, amount, odds, "Sports");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // places an esports bet
    @PostMapping("/esports/bet")
    public ResponseEntity<?> placeEsportsBet(
            @RequestParam String username,
            @RequestParam String game,
            @RequestParam double amount) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Double odds = ESPORTS_ODDS.get(game);
        if (odds == null) {
            return ResponseEntity.badRequest().body("Game not found. Available: " + ESPORTS_ODDS.keySet());
        }

        try {
            return bettingClient.placeBet(username, amount, odds, "Esports");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // places a chancer bet
    @PostMapping("/chancer/bet")
    public ResponseEntity<?> placeChancerBet(
            @RequestParam String username,
            @RequestParam String game,
            @RequestParam double amount) {

        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Double odds = CHANCER_ODDS.get(game);
        if (odds == null) {
            return ResponseEntity.badRequest().body("Game not found. Available: " + CHANCER_ODDS.keySet());
        }

        try {
            return bettingClient.placeBet(username, amount, odds, "Chancer");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // shows all bets for a user
    @GetMapping("/my-bets")
    public ResponseEntity<?> myBets(@RequestParam String username) {
        if (!userService.userExists(username)) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok(bettingClient.getBets(username));
    }
}