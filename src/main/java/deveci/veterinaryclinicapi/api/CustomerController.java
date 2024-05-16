package deveci.veterinaryclinicapi.api;

import deveci.veterinaryclinicapi.business.abstracts.CustomerService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.customer.CustomerSaveRequest;
import deveci.veterinaryclinicapi.dto.request.customer.CustomerUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.customer.CustomerResponse;
import deveci.veterinaryclinicapi.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapperService modelMapper;

    public CustomerController(CustomerService customerService, ModelMapperService modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest){

        Customer saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest, Customer.class);
        this.customerService.save(saveCustomer);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer, CustomerResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") Long id) {
        Customer customer = this.customerService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(customer, CustomerResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        Customer updateCustomer = this.modelMapper.forRequest().map(customerUpdateRequest, Customer.class);
        this.customerService.update(updateCustomer);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateCustomer, CustomerResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.customerService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize) {
        Page<Customer> customerPage = this.customerService.cursor(page, pageSize);
        Page<CustomerResponse> customerResponsePage = customerPage
                .map(category -> this.modelMapper.forResponse().map(category, CustomerResponse.class));

        return ResultHelper.cursor(customerResponsePage);
    }
}
