package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Appointment;
import deveci.veterinaryclinicapi.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);

    boolean existsByAppointmentDateBetweenAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Doctor doctor);

    boolean existsByAppointmentDateBetweenAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Animal animal);

    List<Appointment> findByAppointmentDateBetweenAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Doctor doctor);

    List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Animal animal);

    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE DATE(a.appointmentDate) = :date AND a.doctor.id = :doctorId")
    boolean existsByAppointmentDateAndDoctorId(@Param("date") LocalDate date, @Param("doctorId") Long doctorId);
}
