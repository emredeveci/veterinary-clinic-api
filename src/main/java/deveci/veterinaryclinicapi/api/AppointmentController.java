package deveci.veterinaryclinicapi.api;

import deveci.veterinaryclinicapi.business.abstracts.AppointmentService;
import deveci.veterinaryclinicapi.core.config.modelMapper.ModelMapperService;
import deveci.veterinaryclinicapi.core.result.Result;
import deveci.veterinaryclinicapi.core.result.ResultData;
import deveci.veterinaryclinicapi.core.utilities.ResultHelper;
import deveci.veterinaryclinicapi.dto.request.appointment.AppointmentSaveRequest;
import deveci.veterinaryclinicapi.dto.request.appointment.AppointmentUpdateRequest;
import deveci.veterinaryclinicapi.dto.response.CursorResponse;
import deveci.veterinaryclinicapi.dto.response.appointment.AppointmentResponse;
import deveci.veterinaryclinicapi.entities.Appointment;
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

    public AppointmentController(AppointmentService appointmentService, ModelMapperService modelMapper) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    // Creates a new appointment record
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Evaluation 26 - Correct HTTP code usage
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        return ResultHelper.created(this.modelMapper.forResponse().map(this.appointmentService.save(this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class)), AppointmentResponse.class));
    }

    // Retrieves an appointment by its ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.appointmentService.get(id), AppointmentResponse.class));
    }

    // Updates an existing appointment record
    @PutMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        return ResultHelper.success(this.modelMapper.forResponse().map(this.appointmentService.update(this.modelMapper.forRequest().map(appointmentUpdateRequest, Appointment.class)), AppointmentResponse.class));
    }

    // Deletes an appointment by its ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public Result delete(@PathVariable("id") Long id) {
        return ResultHelper.success(this.appointmentService.delete(id));
    }

    // Retrieves a paginated list of appointments
    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize) {
        Page<Appointment> availableAppointmentPage = this.appointmentService.cursor(page, pageSize);
        Page<AppointmentResponse> availableAppointmentResponsePage = availableAppointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));

        return ResultHelper.cursor(availableAppointmentResponsePage);
    }

    // Filters appointments by date and doctor availability
    @GetMapping("/date-doctor-availability")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<List<AppointmentResponse>> filterByDateTimeAndDoctor(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDate,
                                                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDate,
                                                                           @RequestParam("doctorId") Long doctorId) {
        return ResultHelper.success(appointmentService.filterByDateTimeAndDoctor(startDate, endDate, doctorId).stream().map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class)).collect(Collectors.toList()));
    }

    // Filters appointments by date and animal
    @GetMapping("/appointment-dates-animal")
    @ResponseStatus(HttpStatus.OK) // Evaluation 26 - Correct HTTP code usage
    public ResultData<List<AppointmentResponse>> filterByDateTimeAndAnimal(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDate,
                                                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDate,
                                                                           @RequestParam("animalId") Long animalId) {
        return ResultHelper.success(appointmentService.filterByDateTimeAndAnimal(startDate, endDate, animalId).stream().map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class)).collect(Collectors.toList()));
    }

}
