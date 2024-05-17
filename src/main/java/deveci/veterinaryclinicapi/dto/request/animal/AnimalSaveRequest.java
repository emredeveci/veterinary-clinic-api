package deveci.veterinaryclinicapi.dto.request.animal;

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
