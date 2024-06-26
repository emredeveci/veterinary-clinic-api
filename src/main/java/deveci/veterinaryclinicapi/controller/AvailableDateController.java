package deveci.veterinaryclinicapi.controller;

import deveci.veterinaryclinicapi.service.abstracts.AvailableDateService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.availabledate.AvailableDateSaveRequest;
import deveci.veterinaryclinicapi.dto.request.availabledate.AvailableDateUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.availabledate.AvailableDateResponse;
import deveci.veterinaryclinicapi.entities.AvailableDate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/availabledates")
public class AvailableDateController {

    private final AvailableDateService availableDateService;
    private final ModelMapperService modelMapper;

    public AvailableDateController(AvailableDateService availableDateService, ModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
    }

    // Creates a new available date record
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Evaluation 26 - Correct HTTP code usage
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.availableDateService.save(this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class)), AvailableDateResponse.class));
    }

    // Retrieves an available date by its ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<AvailableDateResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.availableDateService.get(id), AvailableDateResponse.class));
    }

    // Updates an existing available date record
    @PutMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.availableDateService.update(this.modelMapper.forRequest().map(availableDateUpdateRequest, AvailableDate.class)), AvailableDateResponse.class));
    }

    // Deletes an available date by its ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public Result delete(@PathVariable("id") Long id) {
        return ResultHelper.success(this.availableDateService.delete(id));
    }

    // Retrieves a paginated list of available dates
    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Page<AvailableDate> availableDatePage = this.availableDateService.cursor(page, pageSize);
        Page<AvailableDateResponse> availableDateResponsePage = availableDatePage
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));

        return ResultHelper.cursor(availableDateResponsePage);
    }
}
