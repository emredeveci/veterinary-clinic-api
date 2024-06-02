package deveci.veterinaryclinicapi.service.concretes;

import deveci.veterinaryclinicapi.service.abstracts.AvailableDateService;
import deveci.veterinaryclinicapi.core.exception.DateException;
import deveci.veterinaryclinicapi.core.exception.ExistingRecordsException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
import deveci.veterinaryclinicapi.dao.AppointmentRepo;
import deveci.veterinaryclinicapi.dao.AvailableDateRepo;
import deveci.veterinaryclinicapi.dao.DoctorRepo;
import deveci.veterinaryclinicapi.entities.AvailableDate;
import deveci.veterinaryclinicapi.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateServiceImpl implements AvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;

    public AvailableDateServiceImpl(AvailableDateRepo availableDateRepo, DoctorRepo doctorRepo, AppointmentRepo appointmentRepo) {
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
    }

    // Evaluation 16 - Create an available date entry for a doctor
    @Override
    public AvailableDate save(AvailableDate availableDate) {
        Doctor doctor = this.doctorRepo.findById(availableDate.getDoctor().getId()).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));

        if (availableDateRepo.existsByAvailableDateAndDoctorId(availableDate.getAvailableDate(), availableDate.getDoctor().getId())) {
            throw new ExistingRecordsException(Msg.AVAILABLE_DATE_EXISTS);
        }

        availableDate.setDoctor(doctor);
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(Long id) {
        // Evaluation 25 - Check if the available date exists. If not, throw an error.
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_AVAILABLE_DATE_ID));
    }

    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.availableDateRepo.findAll(pageable);
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        // Evaluation 25 - Check if the available date exists. If not, throw an error.
        AvailableDate existingAvailableDate = availableDateRepo.findById(availableDate.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_AVAILABLE_DATE_ID));

        // Checks if the available date is being changed
        if (!existingAvailableDate.getAvailableDate().equals(availableDate.getAvailableDate())) {
            // Checks if there is already an appointment on that day
            if (appointmentRepo.existsByAppointmentDateAndDoctorId(existingAvailableDate.getAvailableDate(), existingAvailableDate.getDoctor().getId())) {
                throw new DateException(Msg.EXISTING_APPOINTMENT);
            }
        }

        // Checks if the doctor is being changed
        if (!existingAvailableDate.getDoctor().getId().equals(availableDate.getDoctor().getId())) {
            // Checks if the existing doctor has an appointment on the available date
            if (appointmentRepo.existsByAppointmentDateAndDoctorId(existingAvailableDate.getAvailableDate(), existingAvailableDate.getDoctor().getId())) {
                throw new DateException(Msg.EXISTING_APPOINTMENT);
            }

            // Checks if the new doctor exists
            if (doctorRepo.findById(availableDate.getDoctor().getId()).isEmpty()) {
                throw new NotFoundException(Msg.NO_SUCH_DOCTOR_ID);
            }
        }

        // Checks if the available date already exists for the new doctor
        if (availableDateRepo.existsByAvailableDateAndDoctorId(availableDate.getAvailableDate(), availableDate.getDoctor().getId())) {
            throw new ExistingRecordsException(Msg.AVAILABLE_DATE_EXISTS);
        }

        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(Long id) {

        // Evaluation 25 - Check if the available date exists. If not, throw an error.
        AvailableDate existingAvailableDate = availableDateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_AVAILABLE_DATE_ID));

        // Checks if there are any appointments made on the date associated with the available date
        if (appointmentRepo.existsByAppointmentDateAndDoctorId(existingAvailableDate.getAvailableDate(), existingAvailableDate.getDoctor().getId())) {
            throw new ExistingRecordsException(Msg.EXISTING_APPOINTMENT);
        }
        this.availableDateRepo.delete(this.get(id));
        return true;
    }
}
