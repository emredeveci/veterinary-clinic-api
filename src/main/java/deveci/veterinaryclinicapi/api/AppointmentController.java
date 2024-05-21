package deveci.veterinaryclinicapi.api;

import deveci.veterinaryclinicapi.business.abstracts.AnimalService;
import deveci.veterinaryclinicapi.business.abstracts.AppointmentService;
import deveci.veterinaryclinicapi.business.abstracts.DoctorService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.appointment.AppointmentSaveRequest;
import deveci.veterinaryclinicapi.dto.request.appointment.AppointmentUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.appointment.AppointmentResponse;
import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Appointment;
import deveci.veterinaryclinicapi.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ModelMapperService modelMapper;
    private final AnimalService animalService;
    private final DoctorService doctorService;

    public AppointmentController(AppointmentService appointmentService, ModelMapperService modelMapper, AnimalService animalService, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.doctorService = doctorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.appointmentService.save(this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class)), AppointmentResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.appointmentService.get(id), AppointmentResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.appointmentService.update(this.modelMapper.forRequest().map(appointmentUpdateRequest, Appointment.class)), AppointmentResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        return ResultHelper.success(this.appointmentService.delete(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Page<Appointment> availableAppointmentPage = this.appointmentService.cursor(page, pageSize);
        Page<AppointmentResponse> availableAppointmentResponsePage = availableAppointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));

        return ResultHelper.cursor(availableAppointmentResponsePage);
    }

    @GetMapping("/date-doctor-availability")
    public ResultData<List<AppointmentResponse>> filterByDateTimeAndDoctor(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                               @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                               @RequestParam("doctorId") Long doctorId) {
        return ResultHelper.success(appointmentService.filterByDateTimeAndDoctor(startDate, endDate, doctorId).stream().map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class)).collect(Collectors.toList()));
    }

    @GetMapping("/appointment-dates-animal")
    public ResultData<List<AppointmentResponse>> filterByDateTimeAndAnimal(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                               @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                               @RequestParam("animalId") Long animalId) {
        return ResultHelper.success(appointmentService.filterByDateTimeAndAnimal(startDate, endDate, animalId).stream().map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class)).collect(Collectors.toList()));
    }

}
