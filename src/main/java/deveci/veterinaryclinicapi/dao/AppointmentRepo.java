package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    boolean existsByAvailableDateId(long id);
}
