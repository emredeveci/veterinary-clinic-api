package deveci.veterinaryclinicapi.dao;

import deveci.veterinaryclinicapi.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    List<Animal> getAnimalByCustomerName(String name);

    List<Animal> getAnimalByName(String name);

    List<Animal> getAnimalByCustomerId(Long customerId);

}
