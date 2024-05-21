package deveci.veterinaryclinicapi.business.concretes;

import deveci.veterinaryclinicapi.business.abstracts.AppointmentService;
import deveci.veterinaryclinicapi.core.exception.DateException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
import deveci.veterinaryclinicapi.dao.AnimalRepo;
import deveci.veterinaryclinicapi.dao.AppointmentRepo;
import deveci.veterinaryclinicapi.dao.AvailableDateRepo;
import deveci.veterinaryclinicapi.dao.DoctorRepo;
import deveci.veterinaryclinicapi.entities.Animal;
import deveci.veterinaryclinicapi.entities.Appointment;
import deveci.veterinaryclinicapi.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final AvailableDateRepo availableDateRepo;
    private final AnimalRepo animalRepo;

    public AppointmentManager(AppointmentRepo appointmentRepo, DoctorRepo doctorRepo, AvailableDateRepo availableDateRepo, AnimalRepo animalRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.availableDateRepo = availableDateRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public Appointment save(Appointment appointment) {

        this.animalRepo.findById(appointment.getAnimal().getId()).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_ANIMAL_ID));

        Doctor doctor = this.doctorRepo.findById(appointment.getDoctor().getId()).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));

        if(!doctor.getDateList().stream()
                .anyMatch(availableDate -> availableDate.getAvailableDate().equals(appointment.getAppointmentDate().toLocalDate()))){
            throw new DateException(Msg.OUT_OF_OFFICE);
        }

        if(appointmentRepo.existsByDoctorIdAndAppointmentDate(
                appointment.getDoctor().getId(), appointment.getAppointmentDate())){
            throw new DateException(Msg.APPOINTMENT_ALREADY_TAKEN);
        }

        return appointmentRepo.save(appointment);
    }

    @Override
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_APPOINTMENT_ID));
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.appointmentRepo.findAll(pageable);
    }

    @Override
    public Appointment update(Appointment appointment) {
        this.get(appointment.getId());
        return this.save(appointment);
    }

    @Override
    public boolean delete(Long id) {
        this.appointmentRepo.delete(this.get(id));
        return true;
    }

    @Override
    public List<Appointment> filterByDateTimeAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Long doctorId) {
        Doctor existingDoctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));

        if (!appointmentRepo.existsByAppointmentDateBetweenAndDoctor(startDate, endDate, existingDoctor)) {
            throw new NotFoundException(Msg.NO_APPOINTMENT_RECORDS);
        }
        return appointmentRepo.findByAppointmentDateBetweenAndDoctor(startDate, endDate, existingDoctor);
    }

    @Override
    public List<Appointment> filterByDateTimeAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Long animalId) {
        Animal existingAnimal = animalRepo.findById(animalId)
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_ANIMAL_ID));
        if (!appointmentRepo.existsByAppointmentDateBetweenAndAnimal(startDate, endDate, existingAnimal)) {
            throw new NotFoundException(Msg.NO_APPOINTMENT_RECORDS);
        }
        return appointmentRepo.findByAppointmentDateBetweenAndAnimal(startDate, endDate, existingAnimal);
    }
}
