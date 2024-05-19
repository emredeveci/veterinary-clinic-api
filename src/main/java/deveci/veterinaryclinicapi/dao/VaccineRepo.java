package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    boolean existsVaccineByCodeAndNameAndAnimalId(String code, String name, Long id);

    List<Vaccine> findByCodeAndNameAndAnimalId(String code, String name, Long id);

    List<Vaccine> findByProtectionEndDateAfterOrderByProtectionEndDate(LocalDate endDate);

    List<Vaccine> findByAnimalId(Long animalId);

    List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate);
}
