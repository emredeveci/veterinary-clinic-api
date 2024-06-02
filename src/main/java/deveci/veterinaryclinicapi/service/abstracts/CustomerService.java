package deveci.veterinaryclinicapi.service.abstracts;

import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);

    Customer get(Long id);

    Page<Customer> cursor(int page, int pageSize);

    Customer update(Customer product);

    boolean delete(Long id);

    List<Customer> getByCustomerName(String name);


    List<Animal> getByAnimalList(Long id);
}
