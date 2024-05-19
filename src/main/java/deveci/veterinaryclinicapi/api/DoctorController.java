package deveci.veterinaryclinicapi.api;

import deveci.veterinaryclinicapi.business.abstracts.DoctorService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.doctor.DoctorSaveRequest;
import deveci.veterinaryclinicapi.dto.request.doctor.DoctorUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.doctor.DoctorResponse;
import deveci.veterinaryclinicapi.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapperService modelMapper;


    public DoctorController(DoctorService doctorService, ModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.doctorService.save(this.modelMapper.forRequest().map(doctorSaveRequest, Doctor.class)), DoctorResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.doctorService.get(id), DoctorResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.doctorService.update(this.modelMapper.forRequest().map(doctorUpdateRequest, Doctor.class)), DoctorResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        return ResultHelper.deleted(doctorService.delete(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Page<Doctor> doctorPage = this.doctorService.cursor(page, pageSize);
        Page<DoctorResponse> doctorResponsePage = doctorPage
                .map(doctor -> this.modelMapper.forResponse().map(doctor, DoctorResponse.class));

        return ResultHelper.cursor(doctorResponsePage);
    }
}
