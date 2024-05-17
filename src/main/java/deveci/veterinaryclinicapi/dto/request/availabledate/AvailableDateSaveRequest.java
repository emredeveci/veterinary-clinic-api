package deveci.veterinaryclinicapi.dto.request.availabledate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateSaveRequest {

    @NotNull(message = "Date cannot be empty or null.")
    private LocalDate availableDate;

    @NotNull(message = "Doctor cannot be empty or null.")
    private Long doctor;
}
