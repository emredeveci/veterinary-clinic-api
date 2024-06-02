package deveci.veterinaryclinicapi.service.concretes;

import deveci.veterinaryclinicapi.service.abstracts.CustomerService;
import deveci.veterinaryclinicapi.core.exception.ExistingRecordsException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    // Evaluation 10 - Create a pet owner entry
    @Override
    public Customer save(Customer customer) {
        // Checks if the new email or phone number is already taken by another customer
        Optional<Customer> customerByEmail = this.customerRepo.findByEmail(customer.getEmail());
        Optional<Customer> customerByPhone = this.customerRepo.findByPhone(customer.getPhone());

        if (customerByEmail.isPresent()) {
            throw new ExistingRecordsException(Msg.CUSTOMER_EMAIL_EXISTS);
        }

        if (customerByPhone.isPresent()) {
            throw new ExistingRecordsException(Msg.CUSTOMER_PHONE_EXISTS);
        }

        // Proceed with the update
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(Long id) {
        // Evaluation 25 - Check if the customer exists. If not, throw an error.
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_CUSTOMER_ID));
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.customerRepo.findAll(pageable);
    }

    @Override
    public Customer update(Customer customer) {

        // Evaluation 25 - Check if the customer exists. If not, throw an error.
        Customer existingCustomer = this.customerRepo.findById(customer.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_CUSTOMER_ID));

        Optional<Customer> customerByEmail = this.customerRepo.findByEmail(customer.getEmail());
        Optional<Customer> customerByPhone = this.customerRepo.findByPhone(customer.getPhone());

        // Checks if the new email is already taken by another customer
        if (customerByEmail.isPresent() && !customerByEmail.get().getId().equals(customer.getId())) {
            throw new ExistingRecordsException(Msg.CUSTOMER_EMAIL_EXISTS);
        }

        // Checks if the new phone number is already taken by another customer
        if (customerByPhone.isPresent() && !customerByPhone.get().getId().equals(customer.getId())) {
            throw new ExistingRecordsException(Msg.CUSTOMER_PHONE_EXISTS);
        }

        // Proceed with the update
        return this.customerRepo.save(customer);
    }

    @Override
    public boolean delete(Long id) {
        // Evaluation 25 - Check if the customer exists. If not, throw an error.
        this.customerRepo.delete(this.get(id));
        return true;
    }

    // Evaluation 11 - Filter animals by owner/customer name
    @Override
    public List<Customer> getByCustomerName(String name) {
        if (customerRepo.findByName(name).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }
        return customerRepo.findByName(name);
    }

    // Evaluation 14 - Retrieve the animal(s) of a customer by using their ID
    @Override
    public List<Animal> getByAnimalList(Long id) {
        if (customerRepo.findById(id).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }
        if (this.get(id).getAnimalList().isEmpty()) {
            throw new NotFoundException(Msg.NO_DATA_CRITERIA);
        }
        return get(id).getAnimalList();
    }
}
