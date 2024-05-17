package deveci.veterinaryclinicapi.dto.request.animal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {

    @Positive(message = "Customer ID needs to be a positive number.")
    @NotNull(message = "Customer ID cannot be null.")
    private Long id;

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String name;

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String species;

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String breed;

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String gender;

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String color;

    @NotNull(message = "Name cannot be empty or null.")
    private LocalDate dateOfBirth;

    @NotNull(message = "Name cannot be empty or null.")
    private Long customer;
}
