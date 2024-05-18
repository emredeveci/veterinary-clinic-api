package deveci.veterinaryclinicapi.dto.request.availabledate;

import deveci.veterinaryclinicapi.entities.Doctor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateUpdateRequest {

    @Positive(message = "Date ID needs to be a positive number.")
    @NotNull(message = "Date ID cannot be null.")
    private Long id;

    @NotNull(message = "Date cannot be empty or null.")
    private LocalDate availableDate;

    @NotNull(message = "Doctor ID cannot be empty or null.")
    private Doctor doctor;
}
