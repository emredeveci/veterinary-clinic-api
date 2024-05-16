package deveci.veterinaryclinicapi.business.abstracts;

import deveci.veterinaryclinicapi.entities.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer save(Customer customer);

    Customer get(Long id);

    Page<Customer> cursor(int page, int pageSize);

    Customer update(Customer product);

    boolean delete(Long id);
}
