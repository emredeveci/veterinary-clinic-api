package deveci.veterinaryclinicapi.business.concretes;

import deveci.veterinaryclinicapi.business.abstracts.DoctorService;
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
public class DoctorManager implements DoctorService {

    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public Doctor save(Doctor doctor) {

        // Check if the new email or phone number is already taken by another doctor
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
        return this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));
    }

    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.doctorRepo.findAll(pageable);
    }

    @Override
    public Doctor update(Doctor doctor) {

        // Check if the doctor to be updated exists
        Doctor existingDoctor = this.doctorRepo.findById(doctor.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_DOCTOR_ID));

        // Check if the new email or phone number is already taken by another doctor
        Optional<Doctor> doctorByEmail = this.doctorRepo.findByEmail(doctor.getEmail());
        Optional<Doctor> doctorByPhone = this.doctorRepo.findByPhone(doctor.getPhone());

        if (doctorByEmail.isPresent() && !doctorByEmail.get().getId().equals(doctor.getId())) {
            throw new ExistingRecordsException(Msg.DR_EMAIL_EXISTS);
        }

        if (doctorByPhone.isPresent() && !doctorByPhone.get().getId().equals(doctor.getId())) {
            throw new ExistingRecordsException(Msg.DR_PHONE_EXISTS);
        }

        return this.doctorRepo.save(doctor);
    }

    @Override
    public boolean delete(Long id) {
        this.doctorRepo.delete(this.get(id));
        return true;
    }
}
