package deveci.veterinaryclinicapi.service.concretes;

import deveci.veterinaryclinicapi.service.abstracts.AnimalService;
import deveci.veterinaryclinicapi.core.exception.ExistingRecordsException;
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
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepo animalRepo;
    private final CustomerRepo customerRepo;

    public AnimalServiceImpl(AnimalRepo animalRepo, CustomerRepo customerRepo) {
        this.animalRepo = animalRepo;
        this.customerRepo = customerRepo;
    }

    // Evaluation 12 - Create an animal entry
    @Override
    public Animal save(Animal animal) {

        Optional<Customer> optionalCustomer = customerRepo.findById(animal.getCustomer().getId());

        // Checks if the customer exists
        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }

        Customer customer = optionalCustomer.get();

        // Checks if the customer already has an animal with the same name
        boolean animalExists = customer.getAnimalList().stream()
                .anyMatch(existingAnimal -> existingAnimal.getName().equals(animal.getName()));

        if (animalExists) {
            throw new ExistingRecordsException(Msg.DUPLICATE_ANIMAL);
        }

        animal.setCustomer(customer);
        // Save the new animal
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {
        // Evaluation 25 - Check if the animal exists. If not, throw an error.
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_ANIMAL_ID));
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.animalRepo.findAll(pageable);
    }

    @Override
    public Animal update(Animal animal) {

        // Evaluation 25 - Checks if the animal exists. If not, throw an error.
        animalRepo.findById(animal.getId()).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_ANIMAL_ID));

        // Evaluation 25 - Checks if the customer exists. If not, throw an error.
        if (customerRepo.findById(animal.getCustomer().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }

        // update the animal
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(Long id) {
        //Evaluation 25 - Check if the animal exists. If not, throw an error.
        this.animalRepo.delete(this.get(id));
        return true;
    }

    @Override
    public List<Animal> getAnimalByCustomerName(String name) {
        if (animalRepo.getAnimalByCustomerName(name).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }
        return animalRepo.getAnimalByCustomerName(name);
    }

    // Evaluation 13 - Filter animals by name
    @Override
    public List<Animal> getAnimalByName(String name) {
        if (animalRepo.getAnimalByName(name).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL);
        }
        return animalRepo.getAnimalByName(name);
    }

    @Override
    public List<Animal> getAnimalByCustomerId(Long id) {
        this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_CUSTOMER_ID));
        if (animalRepo.getAnimalByCustomerId(id).isEmpty()) {
            throw new NotFoundException(Msg.NO_DATA_CRITERIA);
        }
        return animalRepo.getAnimalByCustomerId(id);
    }
}
