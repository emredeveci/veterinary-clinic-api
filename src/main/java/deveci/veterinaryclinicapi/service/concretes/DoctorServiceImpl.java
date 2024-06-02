package deveci.veterinaryclinicapi.service.concretes;

import deveci.veterinaryclinicapi.service.abstracts.DoctorService;
import deveci.veterinaryclinicapi.core.exception.ExistingRecordsException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
import deveci.veterinaryclinicapi.dao.DoctorRepo;
import deveci.veterinaryclinicapi.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepo doctorRepo;

    public DoctorServiceImpl(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    // Evaluation 15 - Create a doctor entry
    @Override
    public Doctor save(Doctor doctor) {

        // Checks if the new email or phone number is already taken by another doctor
        Optional<Doctor> doctorByEmail = this.doctorRepo.findByEmail(doctor.getEmail());
        Optional<Doctor> doctorByPhone = this.doctorRepo.findByPhone(doctor.getPhone());

        if (doctorByEmail.isPresent()) {
            throw new ExistingRecordsException(Msg.DR_EMAIL_EXISTS);
        }

        if (doctorByPhone.isPresent()) {
            throw new ExistingRecordsException(Msg.DR_PHONE_EXISTS);
        }

        // Proceed with the update
        return this.doctorRepo.save(doctor);
    }

    @Override
    public Doctor get(Long id) {
        // Evaluation 25 - Check if the doctor exists. If not, throw an error.
        return this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));
    }

    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.doctorRepo.findAll(pageable);
    }

    @Override
    public Doctor update(Doctor doctor) {

        // Evaluation 25 - Check if the doctor exists. If not, throw an error.
        Doctor existingDoctor = this.doctorRepo.findById(doctor.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));

        Optional<Doctor> doctorByEmail = this.doctorRepo.findByEmail(doctor.getEmail());
        Optional<Doctor> doctorByPhone = this.doctorRepo.findByPhone(doctor.getPhone());

        // Checks if the new email is already taken by another doctor
        if (doctorByEmail.isPresent() && !doctorByEmail.get().getId().equals(doctor.getId())) {
            throw new ExistingRecordsException(Msg.DR_EMAIL_EXISTS);
        }

        // Checks if the new phone number is already taken by another doctor
        if (doctorByPhone.isPresent() && !doctorByPhone.get().getId().equals(doctor.getId())) {
            throw new ExistingRecordsException(Msg.DR_PHONE_EXISTS);
        }

        return this.doctorRepo.save(doctor);
    }

    @Override
    public boolean delete(Long id) {
        // Evaluation 25 - Check if the doctor exists. If not, throw an error.
        this.doctorRepo.delete(this.get(id));
        return true;
    }
}
