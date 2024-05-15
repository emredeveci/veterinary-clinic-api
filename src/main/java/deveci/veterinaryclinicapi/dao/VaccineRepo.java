package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {
}
