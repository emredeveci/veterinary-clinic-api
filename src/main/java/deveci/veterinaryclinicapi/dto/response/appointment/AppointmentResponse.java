package deveci.veterinaryclinicapi.dto.response.appointment;

import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private Animal animal;
    private Doctor doctor;
}
