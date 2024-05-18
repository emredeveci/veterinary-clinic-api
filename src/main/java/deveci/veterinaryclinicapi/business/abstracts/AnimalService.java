package deveci.veterinaryclinicapi.business.abstracts;

import deveci.veterinaryclinicapi.entities.Animal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AnimalService {
    Animal save(Animal animal);

    Animal get(Long id);

    Page<Animal> cursor(int page, int pageSize);

    Animal update(Animal animal);

    boolean delete(Long id);

    List<Animal> getAnimalByCustomerName(String name);

    List<Animal> getAnimalByName(String name);
    List<Animal> getAnimalByCustomerId(Long id);
}
