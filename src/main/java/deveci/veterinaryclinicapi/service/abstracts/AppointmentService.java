package deveci.veterinaryclinicapi.service.abstracts;

import deveci.veterinaryclinicapi.entities.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment save(Appointment appointment);

    Appointment get(Long id);

    Page<Appointment> cursor(int page, int pageSize);

    Appointment update(Appointment appointment);

    boolean delete(Long id);

    List<Appointment> filterByDateTimeAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Long doctorId);


    List<Appointment> filterByDateTimeAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Long animalId);
}
