package deveci.veterinaryclinicapi.dto.request.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Doctor;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaveRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentDate;

    @NotNull(message = "Animal ID cannot be empty or null.")
    private Animal animal;

    @NotNull(message = "Doctor ID cannot be empty or null.")
    private Doctor doctor;
}
