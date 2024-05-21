package deveci.veterinaryclinicapi.dto.response.availabledate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedAvailableDateResponse {
    private LocalDate availableDate;
}
