package deveci.veterinaryclinicapi.api;


import deveci.veterinaryclinicapi.business.abstracts.AnimalService;
import deveci.veterinaryclinicapi.business.abstracts.CustomerService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.animal.AnimalSaveRequest;
import deveci.veterinaryclinicapi.dto.request.animal.AnimalUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.animal.AnimalResponse;
import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final ModelMapperService modelMapper;
    private final CustomerService customerService;

    public AnimalController(AnimalService animalService, ModelMapperService modelMapper, CustomerService customerService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.animalService.get(id), AnimalResponse.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.animalService.save(this.modelMapper.forRequest().map(animalSaveRequest, Animal.class)), AnimalResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.animalService.update(this.modelMapper.forRequest().map(animalUpdateRequest, Animal.class)), AnimalResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.animalService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize) {
        Page<Animal> animalPage = this.animalService.cursor(page, pageSize);
        Page<AnimalResponse> animalResponsePage = animalPage
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class));

        return ResultHelper.cursor(animalResponsePage);
    }

    //end point for searching by customer name
    @GetMapping("/by-customer-name")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalByCustomerName(@RequestParam String name) {
        return ResultHelper.success(animalService.getAnimalByCustomerName(name).stream().map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class)).collect(Collectors.toList()));
    }

    //end point for searching by animal name
    @GetMapping("/by-name")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalByName(@RequestParam String name) {
        return ResultHelper.success(animalService.getAnimalByName(name).stream().map(animal -> modelMapper.forResponse().map(animal, AnimalResponse.class)).collect(Collectors.toList()));
    }

    //end point for searching all animals that belong to a customer
    @GetMapping("/by-customer-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalResponse> getCustomerId(@PathVariable("id") long id) {
        return animalService.getAnimalByCustomerId(id).stream().map(animal -> modelMapper.forResponse().map(animal, AnimalResponse.class)).collect(Collectors.toList());
    }
}
