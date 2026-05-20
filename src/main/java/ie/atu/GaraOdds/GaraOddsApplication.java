package ie.atu.GaraOdds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication // marks this as a spring boot application
@EnableFeignClients
public class GaraOddsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaraOddsApplication.class, args);
        // http://localhost:8080/swagger-ui/index.html
    }
}