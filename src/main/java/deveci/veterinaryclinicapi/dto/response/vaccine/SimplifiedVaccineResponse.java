package deveci.veterinaryclinicapi.dto.response.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedVaccineResponse {
    private String name;
    private LocalDate protectionStartDate;
    private LocalDate protectionEndDate;
}
