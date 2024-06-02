package deveci.veterinaryclinicapi.service.concretes;

import deveci.veterinaryclinicapi.service.abstracts.AppointmentService;
import deveci.veterinaryclinicapi.core.exception.DateException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
import deveci.veterinaryclinicapi.dao.AnimalRepo;
import deveci.veterinaryclinicapi.dao.AppointmentRepo;
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
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final AnimalRepo animalRepo;

    public AppointmentServiceImpl(AppointmentRepo appointmentRepo, DoctorRepo doctorRepo, AnimalRepo animalRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.animalRepo = animalRepo;
    }

    // Evaluation 17 - Create an appointment entry
    @Override
    public Appointment save(Appointment appointment) {

        Animal animal = this.animalRepo.findById(appointment.getAnimal().getId()).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_ANIMAL_ID));

        Doctor doctor = this.doctorRepo.findById(appointment.getDoctor().getId()).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));

        // Evaluation 18 - Checks if the doctor works on the requested date
        if (!doctor.getDateList().stream()
                .anyMatch(availableDate -> availableDate.getAvailableDate().equals(appointment.getAppointmentDate().toLocalDate()))) {
            throw new DateException(Msg.OUT_OF_OFFICE);
        }

        // Evaluation 18 - Checks if there is already an appointment at this date and time
        if (appointmentRepo.existsByDoctorIdAndAppointmentDate(
                appointment.getDoctor().getId(), appointment.getAppointmentDate())) {
            throw new DateException(Msg.APPOINTMENT_ALREADY_TAKEN);
        }

        // Checks if the requested hour fits the appointment hour rule (appointments must start at the beginning of each hour)
        if (!(appointment.getAppointmentDate().getMinute() == 0)) {
            throw new DateException(Msg.HOURLY_APPOINTMENTS);
        }

        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        return appointmentRepo.save(appointment);
    }

    @Override
    public Appointment get(Long id) {
        // Evaluation 25 - Check if the appointment exists. If not, throw an error.
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_APPOINTMENT_ID));
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.appointmentRepo.findAll(pageable);
    }

    @Override
    public Appointment update(Appointment appointment) {
        // Evaluation 25 - Check if the appointment exists. If not, throw an error.
        this.get(appointment.getId());
        return this.save(appointment);
    }

    @Override
    public boolean delete(Long id) {
        // Evaluation 25 - Check if the appointment exists. If not, throw an error.
        this.appointmentRepo.delete(this.get(id));
        return true;
    }

    // Evaluation 20 - Filter appointments by date and doctor
    @Override
    public List<Appointment> filterByDateTimeAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Long doctorId) {
        Doctor existingDoctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));

        if (!appointmentRepo.existsByAppointmentDateBetweenAndDoctor(startDate, endDate, existingDoctor)) {
            throw new NotFoundException(Msg.NO_APPOINTMENT_RECORDS);
        }
        return appointmentRepo.findByAppointmentDateBetweenAndDoctor(startDate, endDate, existingDoctor);
    }

    // Evaluation 19 - Filter appointments by date and animal
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
