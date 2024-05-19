package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndAnimalId(Long doctorId, Long animalId);
    boolean existsByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);
    boolean existsByAppointmentDate(LocalDateTime appointmentDate);

    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE DATE(a.appointmentDate) = :date AND a.doctor.id = :doctorId")
    boolean existsByAppointmentDateAndDoctorId(@Param("date") LocalDate date, @Param("doctorId") Long doctorId);
}
