package ie.atu.GaraOdds.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "garaodds-betting", url = "http://localhost:8081")
public interface GaraOddsBettingClient {

    // places a bet in the betting microservice
    @PostMapping("/bets/place")
    ResponseEntity<String> placeBet(
            @RequestParam String username,
            @RequestParam double amount,
            @RequestParam double odds,
            @RequestParam String category
    );

    // gets all bets for a user from the betting microservice
    @GetMapping("/bets")
    List<?> getBets(@RequestParam String username);
}