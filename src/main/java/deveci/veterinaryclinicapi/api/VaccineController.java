package deveci.veterinaryclinicapi.api;

import deveci.veterinaryclinicapi.business.abstracts.AnimalService;
import deveci.veterinaryclinicapi.business.abstracts.VaccineService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.vaccine.VaccineSaveRequest;
import deveci.veterinaryclinicapi.dto.request.vaccine.VaccineUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.vaccine.VaccineResponse;
import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {

    private final VaccineService vaccineService;
    private final ModelMapperService modelMapper;
    private final AnimalService animalService;

    public VaccineController(VaccineService vaccineService, ModelMapperService modelMapper, AnimalService animalService) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {

        Vaccine saveVaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        Animal animal = this.animalService.get(vaccineSaveRequest.getAnimal());
        saveVaccine.setAnimal(animal);
        this.vaccineService.save(saveVaccine);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        Vaccine vaccine = this.vaccineService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        Animal animal = this.animalService.get(vaccineUpdateRequest.getAnimal());
        updateVaccine.setAnimal(animal);
        this.vaccineService.update(updateVaccine);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize) {
        Page<Vaccine> availableVaccinePage = this.vaccineService.cursor(page, pageSize);
        Page<VaccineResponse> availableVaccineResponsePage = availableVaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));

        return ResultHelper.cursor(availableVaccineResponsePage);
    }
}
