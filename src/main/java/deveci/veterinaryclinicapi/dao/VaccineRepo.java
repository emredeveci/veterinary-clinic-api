package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    boolean existsVaccineByCodeAndNameAndAnimalId(String code, String name, Long id);

    List<Vaccine> findByEndDateAfterOrderByEndDate(LocalDate endDate);

    List<Vaccine> findByAnimalId(Long animalId);

    List<Vaccine> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
}
