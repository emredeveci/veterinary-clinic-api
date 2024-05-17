package deveci.veterinaryclinicapi.dto.request.vaccine;

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
public class VaccineUpdateRequest {

    @Positive(message = "Customer ID needs to be a positive number.")
    @NotNull(message = "Customer ID cannot be null.")
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null.")
    private String name;

    @NotEmpty(message = "Code cannot be empty or null.")
    private String code;

    @NotEmpty(message = "Protection start date cannot be empty or null.")
    private LocalDate protectionStartDate;

    @NotEmpty(message = "Protection end date cannot be empty or null.")
    private LocalDate protectionEndDate;

    @NotEmpty(message = "Animal cannot be empty or null.")
    private Long animal;
}
