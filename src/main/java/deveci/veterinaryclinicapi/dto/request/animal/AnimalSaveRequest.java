package deveci.veterinaryclinicapi.dto.request.animal;

import deveci.veterinaryclinicapi.entities.Customer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String name;

    @NotNull(message = "Species cannot be empty or null.")
    @NotEmpty(message = "Species cannot be empty or null.")
    private String species;

    @NotNull(message = "Breed cannot be empty or null.")
    @NotEmpty(message = "Breed cannot be empty or null.")
    private String breed;

    @NotNull(message = "Gender cannot be empty or null.")
    @NotEmpty(message = "Gender cannot be empty or null.")
    private String gender;

    @NotNull(message = "Color cannot be empty or null.")
    @NotEmpty(message = "Color cannot be empty or null.")
    private String color;

    @NotNull(message = "Date of birth cannot be empty or null.")
    private LocalDate dateOfBirth;

    @NotNull(message = "Customer ID cannot be empty or null.")
    private Customer customer;
}
