package deveci.veterinaryclinicapi.business.abstracts;

import deveci.veterinaryclinicapi.entities.Appointment;
import org.springframework.data.domain.Page;

public interface AppointmentService {

    Appointment save(Appointment appointment);

    Appointment get(Long id);

    Page<Appointment> cursor(int page, int pageSize);

    Appointment update(Appointment appointment);

    boolean delete(Long id);
}
