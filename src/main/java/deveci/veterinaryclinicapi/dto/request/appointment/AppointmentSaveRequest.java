package deveci.veterinaryclinicapi.dto.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaveRequest {

    private LocalDateTime appointmentDate;
    private Long animal;
    private Long doctor;
}
