package deveci.veterinaryclinicapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Veterinary Clinic API", version = "1.0"))
public class VeterinaryClinicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeterinaryClinicApiApplication.class, args);
    }

}
