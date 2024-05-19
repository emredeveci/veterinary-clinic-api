package deveci.veterinaryclinicapi.business.concretes;

import deveci.veterinaryclinicapi.business.abstracts.CustomerService;
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
public class CustomerManager implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer save(Customer customer) {
        // Check if the new email or phone number is already taken by another customer
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
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_CUSTOMER_ID));
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.customerRepo.findAll(pageable);
    }

    @Override
    public Customer update(Customer customer) {

        // Check if the customer to be updated exists
        Customer existingCustomer = this.customerRepo.findById(customer.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_CUSTOMER_ID));

        // Check if the new email or phone number is already taken by another customer
        Optional<Customer> customerByEmail = this.customerRepo.findByEmail(customer.getEmail());
        Optional<Customer> customerByPhone = this.customerRepo.findByPhone(customer.getPhone());

        if (customerByEmail.isPresent() && !customerByEmail.get().getId().equals(customer.getId())) {
            throw new ExistingRecordsException(Msg.CUSTOMER_EMAIL_EXISTS);
        }

        if (customerByPhone.isPresent() && !customerByPhone.get().getId().equals(customer.getId())) {
            throw new ExistingRecordsException(Msg.CUSTOMER_PHONE_EXISTS);
        }

        // Proceed with the update
        return this.customerRepo.save(customer);
    }

    @Override
    public boolean delete(Long id) {
        this.customerRepo.delete(this.get(id));
        return true;
    }

    @Override
    public List<Customer> getByCustomerName(String name) {
        if (customerRepo.findByName(name).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_CUSTOMER);
        }
        return customerRepo.findByName(name);
    }

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
