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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {

    private final VaccineService vaccineService;
    private final ModelMapperService modelMapper;

    public VaccineController(VaccineService vaccineService, ModelMapperService modelMapper) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.vaccineService.save(this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class)), VaccineResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.vaccineService.get(id), VaccineResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.vaccineService.update(this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class)), VaccineResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        return ResultHelper.deleted(vaccineService.delete(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Page<Vaccine> availableVaccinePage = this.vaccineService.cursor(page, pageSize);
        Page<VaccineResponse> availableVaccineResponsePage = availableVaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));

        return ResultHelper.cursor(availableVaccineResponsePage);
    }

    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getAnimalVaccineList(@PathVariable("id") Long id) {
        return ResultHelper.success(vaccineService.getAnimalVaccineList(id).stream().map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)).collect(Collectors.toList()));
    }

    @GetMapping("/protection-check")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getFilterByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // Retrieve a list of vaccines filtered by end date and map the result to response objects.
        return ResultHelper.success(vaccineService.getFilterByStartAndEndDate(startDate, endDate).stream().map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)).collect(Collectors.toList()));
    }
}
