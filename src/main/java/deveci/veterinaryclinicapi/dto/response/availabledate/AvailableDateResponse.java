package deveci.veterinaryclinicapi.dto.response.availabledate;

import deveci.veterinaryclinicapi.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateResponse {

    private Long id;
    private LocalDate availableDate;
    private Doctor doctor;
}
