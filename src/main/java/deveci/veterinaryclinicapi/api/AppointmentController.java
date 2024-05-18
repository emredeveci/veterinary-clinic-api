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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

        Appointment saveAppointment = this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);

        Animal animal = this.animalService.get(appointmentSaveRequest.getAnimal().getId());
        saveAppointment.setAnimal(animal);

        Doctor doctor = this.doctorService.get(appointmentSaveRequest.getDoctor().getId());
        saveAppointment.setDoctor(doctor);

        this.appointmentService.save(saveAppointment);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        Appointment appointment = this.appointmentService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment updateAppointment = this.modelMapper.forRequest().map(appointmentUpdateRequest, Appointment.class);

        Animal animal = this.animalService.get(appointmentUpdateRequest.getAnimal().getId());
        updateAppointment.setAnimal(animal);

        Doctor doctor = this.doctorService.get(appointmentUpdateRequest.getDoctor().getId());
        updateAppointment.setDoctor(doctor);

        this.appointmentService.update(updateAppointment);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAppointment, AppointmentResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize) {
        Page<Appointment> availableAppointmentPage = this.appointmentService.cursor(page, pageSize);
        Page<AppointmentResponse> availableAppointmentResponsePage = availableAppointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));

        return ResultHelper.cursor(availableAppointmentResponsePage);
    }
}
