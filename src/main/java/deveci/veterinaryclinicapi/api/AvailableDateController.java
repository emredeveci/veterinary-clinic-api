package deveci.veterinaryclinicapi.api;

import deveci.veterinaryclinicapi.business.abstracts.AvailableDateService;
import deveci.veterinaryclinicapi.business.abstracts.DoctorService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.availabledate.AvailableDateSaveRequest;
import deveci.veterinaryclinicapi.dto.request.availabledate.AvailableDateUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.availabledate.AvailableDateResponse;
import deveci.veterinaryclinicapi.entities.AvailableDate;
import deveci.veterinaryclinicapi.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/availabledates")
public class AvailableDateController {

    private final AvailableDateService availableDateService;
    private final DoctorService doctorService;
    private final ModelMapperService modelMapper;

    public AvailableDateController(AvailableDateService availableDateService, DoctorService doctorService, ModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {

        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);
        Doctor doctor = this.doctorService.get(availableDateSaveRequest.getDoctor());
        saveAvailableDate.setDoctor(doctor);
        this.availableDateService.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate, AvailableDateResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") Long id) {
        AvailableDate availableDate = this.availableDateService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        AvailableDate updateAvailableDate = this.modelMapper.forRequest().map(availableDateUpdateRequest, AvailableDate.class);
        Doctor doctor = this.doctorService.get(availableDateUpdateRequest.getDoctor());
        updateAvailableDate.setDoctor(doctor);
        this.availableDateService.update(updateAvailableDate);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAvailableDate, AvailableDateResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize) {
        Page<AvailableDate> availableDatePage = this.availableDateService.cursor(page, pageSize);
        Page<AvailableDateResponse> availableDateResponsePage = availableDatePage
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));

        return ResultHelper.cursor(availableDateResponsePage);
    }
}
