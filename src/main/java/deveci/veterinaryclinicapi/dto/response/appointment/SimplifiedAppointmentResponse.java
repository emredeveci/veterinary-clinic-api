package deveci.veterinaryclinicapi.dto.response.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedAppointmentResponse {
    private LocalDateTime appointmentDate;
}
