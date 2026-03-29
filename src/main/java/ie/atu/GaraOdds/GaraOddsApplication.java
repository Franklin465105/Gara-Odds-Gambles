package ie.atu.GaraOdds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // marks this as a spring boot application
public class GaraOddsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaraOddsApplication.class, args);
        // http://localhost:8080/swagger-ui/index.html
    }
}