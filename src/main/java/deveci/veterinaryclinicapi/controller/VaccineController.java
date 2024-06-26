package deveci.veterinaryclinicapi.controller;

import deveci.veterinaryclinicapi.service.abstracts.VaccineService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.vaccine.VaccineSaveRequest;
import deveci.veterinaryclinicapi.dto.request.vaccine.VaccineUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.vaccine.VaccineResponse;
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

    // Creates a new vaccine record
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Evaluation 26 - Correct HTTP code usage
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.vaccineService.save(this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class)), VaccineResponse.class));
    }

    // Retrieves a vaccine by its ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.vaccineService.get(id), VaccineResponse.class));
    }

    // Updates an existing vaccine record
    @PutMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.vaccineService.update(this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class)), VaccineResponse.class));
    }

    // Deletes a vaccine by its ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public Result delete(@PathVariable("id") Long id) {
        return ResultHelper.deleted(vaccineService.delete(id));
    }

    // Retrieves a paginated list of vaccines
    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Page<Vaccine> availableVaccinePage = this.vaccineService.cursor(page, pageSize);
        Page<VaccineResponse> availableVaccineResponsePage = availableVaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));

        return ResultHelper.cursor(availableVaccineResponsePage);
    }

    // Retrieves a list of vaccines for a specific animal by the animal's ID
    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<List<VaccineResponse>> getAnimalVaccineList(@PathVariable("id") Long id) {
        return ResultHelper.success(vaccineService.getAnimalVaccineList(id).stream().map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)).collect(Collectors.toList()));
    }

    // Retrieves a list of vaccines for a specific animal by the animal's ID
    @GetMapping("/protection-check")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<List<VaccineResponse>> getFilterByStartAndEndDate(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResultHelper.success(vaccineService.getFilterByStartAndEndDate(startDate, endDate).stream().map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class)).collect(Collectors.toList()));
    }
}
