package deveci.veterinaryclinicapi.business.concretes;

import deveci.veterinaryclinicapi.business.abstracts.AvailableDateService;
import deveci.veterinaryclinicapi.core.exception.ExistingRecordsException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
import deveci.veterinaryclinicapi.dao.AppointmentRepo;
import deveci.veterinaryclinicapi.dao.AvailableDateRepo;
import deveci.veterinaryclinicapi.dao.DoctorRepo;
import deveci.veterinaryclinicapi.entities.Appointment;
import deveci.veterinaryclinicapi.entities.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateManager implements AvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, DoctorRepo doctorRepo, AppointmentRepo appointmentRepo) {
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        if (doctorRepo.findById(availableDate.getDoctor().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_DOCTOR_ID);
        }
        if (availableDateRepo.existsByDateAndDoctorId(availableDate.getAvailableDate(), availableDate.getDoctor().getId())) {
            throw new ExistingRecordsException(Msg.AVAILABLE_DATE_EXISTS);
        }
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_AVAILABLE_DATE_ID));
    }

    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.availableDateRepo.findAll(pageable);
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        if (appointmentRepo.existsByAvailableDateId(availableDate.getId())) {
            throw new ExistingRecordsException(Msg.EXISTING_APPOINTMENT);
        }
        if (doctorRepo.findById(availableDate.getDoctor().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_DOCTOR_ID);
        }
        if (availableDateRepo.existsByDateAndDoctorId(availableDate.getAvailableDate(), availableDate.getDoctor().getId())) {
            throw new ExistingRecordsException(Msg.AVAILABLE_DATE_EXISTS);
        }
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(Long id) {
        if (appointmentRepo.existsByAvailableDateId(id)) {
            throw new ExistingRecordsException(Msg.EXISTING_APPOINTMENT);
        }
        this.availableDateRepo.delete(this.get(id));
        return true;
    }
}
