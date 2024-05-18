package deveci.veterinaryclinicapi.business.concretes;

import deveci.veterinaryclinicapi.business.abstracts.AnimalService;
import deveci.veterinaryclinicapi.core.exception.ConflictException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
import deveci.veterinaryclinicapi.dao.AnimalRepo;
import deveci.veterinaryclinicapi.dao.CustomerRepo;
import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalManager implements AnimalService {

    private final AnimalRepo animalRepo;
    private final CustomerRepo customerRepo;

    public AnimalManager(AnimalRepo animalRepo, CustomerRepo customerRepo) {
        this.animalRepo = animalRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public Animal save(Animal animal) {

        Optional<Customer> optionalCustomer = customerRepo.findById(animal.getCustomer().getId());

        // Check if the customer exists
        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }

        Customer customer = optionalCustomer.get();

        // Check if the customer already has an animal with the same name
        boolean animalExists = customer.getAnimalList().stream()
                .anyMatch(existingAnimal -> existingAnimal.getName().equals(animal.getName()));

        if (animalExists) {
            throw new ConflictException(Msg.DUPLICATE_ANIMAL);
        }

        // Save the new animal
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.animalRepo.findAll(pageable);
    }

    @Override
    public Animal update(Animal animal) {
        animalRepo.findById(animal.getId()).orElseThrow(()->new NotFoundException(Msg.NO_SUCH_ANIMAL_ID));
        if (customerRepo.findById(animal.getCustomer().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(Long id) {
        this.animalRepo.delete(this.get(id));
        return true;
    }

    @Override
    public List<Animal> getAnimalByCustomerName(String name) {
        if(animalRepo.getAnimalByCustomerName(name).isEmpty()){
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }
        return animalRepo.getAnimalByCustomerName(name);
    }

    @Override
    public List<Animal> getAnimalByName(String name) {
        if(animalRepo.getAnimalByName(name).isEmpty()){
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL);
        }
        return animalRepo.getAnimalByName(name);
    }

    @Override
    public List<Animal> getAnimalByCustomerId(Long id) {
        if(animalRepo.getAnimalByCustomerId(id).isEmpty()){
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }
        return animalRepo.getAnimalByCustomerId(id);
    }
}
