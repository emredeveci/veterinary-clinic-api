package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {
    boolean existsByDateAndDoctorId(LocalDate date, Long id);

}
