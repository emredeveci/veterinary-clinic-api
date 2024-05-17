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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {

        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);
        Customer customer = this.customerService.get(animalSaveRequest.getCustomer());
        saveAnimal.setCustomer(customer);
        this.animalService.save(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal, AnimalResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        Animal animal = this.animalService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(animal, AnimalResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest, Animal.class);
        Customer customer = this.customerService.get(animalUpdateRequest.getCustomer());
        updateAnimal.setCustomer(customer);
        this.animalService.update(updateAnimal);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAnimal, AnimalResponse.class));
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
}
