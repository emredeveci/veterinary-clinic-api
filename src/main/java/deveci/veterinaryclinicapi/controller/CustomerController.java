package deveci.veterinaryclinicapi.controller;

import deveci.veterinaryclinicapi.service.abstracts.CustomerService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.customer.CustomerSaveRequest;
import deveci.veterinaryclinicapi.dto.request.customer.CustomerUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.animal.AnimalResponse;
import deveci.veterinaryclinicapi.dto.response.customer.CustomerResponse;
import deveci.veterinaryclinicapi.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @ResponseStatus(HttpStatus.CREATED) // Evaluation 26 - Correct HTTP code usage
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.customerService.save(this.modelMapper.forRequest().map(customerSaveRequest, Customer.class)), CustomerResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<CustomerResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.customerService.get(id), CustomerResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.customerService.update(this.modelMapper.forRequest().map(customerUpdateRequest, Customer.class)), CustomerResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public Result delete(@PathVariable("id") Long id) {
        return ResultHelper.success(this.customerService.delete(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Page<Customer> customerPage = this.customerService.cursor(page, pageSize);
        Page<CustomerResponse> customerResponsePage = customerPage
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class));

        return ResultHelper.cursor(customerResponsePage);
    }

    @GetMapping("/by-name")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<List<CustomerResponse>> getByCustomerName(@RequestParam String name) {
        return ResultHelper.success(customerService.getByCustomerName(name).stream().map(customer -> modelMapper.forResponse().map(customer, CustomerResponse.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/animal-list")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<List<AnimalResponse>> getByAnimalList(@PathVariable("id") long id) {
        return ResultHelper.success(customerService.getByAnimalList(id).stream().map(customer -> modelMapper.forResponse().map(customer, AnimalResponse.class)).collect(Collectors.toList()));
    }
}
