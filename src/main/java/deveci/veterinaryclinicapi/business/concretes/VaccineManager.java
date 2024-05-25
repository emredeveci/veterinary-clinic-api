package deveci.veterinaryclinicapi.business.concretes;

import deveci.veterinaryclinicapi.business.abstracts.VaccineService;
import deveci.veterinaryclinicapi.core.exception.DateException;
import deveci.veterinaryclinicapi.core.exception.NotFoundException;
import deveci.veterinaryclinicapi.core.utilities.Msg;
import deveci.veterinaryclinicapi.dao.AnimalRepo;
import deveci.veterinaryclinicapi.dao.VaccineRepo;
import deveci.veterinaryclinicapi.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineManager implements VaccineService {

    private final VaccineRepo vaccineRepo;
    private final AnimalRepo animalRepo;

    public VaccineManager(VaccineRepo vaccineRepo, AnimalRepo animalRepo) {
        this.vaccineRepo = vaccineRepo;
        this.animalRepo = animalRepo;
    }

    // Evaluation 21 - Make a vaccine entry with a certain business logic
    @Override
    public Vaccine save(Vaccine vaccine) {
        if (animalRepo.findById(vaccine.getAnimal().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL_ID);
        }

        boolean existsVaccine = vaccineRepo.existsVaccineByCodeAndNameAndAnimalId(
                vaccine.getCode(), vaccine.getName(), vaccine.getAnimal().getId());

        // Evaluation 22 - Checks if this animal already has this vaccine
        if (existsVaccine) {
            List<Vaccine> existingVaccines = vaccineRepo.findByCodeAndNameAndAnimalId(
                    vaccine.getCode(), vaccine.getName(), vaccine.getAnimal().getId());
            LocalDate newProtectionStartDate = vaccine.getProtectionStartDate();
            LocalDate newProtectionEndDate = vaccine.getProtectionEndDate();

            for (Vaccine existingVaccine : existingVaccines) {
                LocalDate existingProtectionEndDate = existingVaccine.getProtectionEndDate();

                // Checks if the new protection start date is before the existing vaccine's protection end date
                if (newProtectionStartDate.isBefore(existingProtectionEndDate)) {
                    throw new DateException(Msg.ACTIVE_PROTECTION);
                }
            }

            //Checks if the protection end date is after the protection start date
            if (!vaccine.getProtectionEndDate().isAfter(vaccine.getProtectionStartDate())) {
                throw new DateException(Msg.END_DATE_ISSUE);
            }
        }

        return vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(Long id) {
        // Evaluation 25 - Check if the vaccine exists. If not, throw an error.
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NO_SUCH_VACCINE_ID));
    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        // Evaluation 25 - Check if the vaccine exists. If not, throw an error.
        this.get(vaccine.getId());
        if (animalRepo.findById(vaccine.getAnimal().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL_ID);
        }

        // Checks if the vaccine already exists for the given animal
        if (vaccineRepo.existsVaccineByCodeAndNameAndAnimalId(vaccine.getCode(), vaccine.getName(), vaccine.getAnimal().getId())) {
            // Checks if there are any vaccines with a protection end date after the current vaccine's protection start date
            if (vaccineRepo.findByProtectionEndDateAfterOrderByProtectionEndDate(vaccine.getProtectionStartDate()).isEmpty()) {
                // Checks if the protection start date is after the protection end date
                if (ChronoUnit.DAYS.between(vaccine.getProtectionStartDate(), vaccine.getProtectionEndDate()) < 0) {
                    throw new DateException(Msg.END_DATE_ISSUE);
                }
                return vaccineRepo.save(vaccine);
            }
            throw new DateException(Msg.ACTIVE_PROTECTION);
        }
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public boolean delete(Long id) {
        // Evaluation 25 - Check if the vaccine exists. If not, throw an error.
        this.vaccineRepo.delete(this.get(id));
        return true;
    }

    // Evaluation  24 - Filter all vaccines of an animal
    @Override
    public List<Vaccine> getAnimalVaccineList(Long id) {
        if (vaccineRepo.findByAnimalId(id).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL_ID);
        }
        return vaccineRepo.findByAnimalId(id);
    }

    // Evaluation 23 - Filter vaccines with a protection end date that are between the requested dates
    @Override
    public List<Vaccine> getFilterByStartAndEndDate(LocalDate startDate, LocalDate endDate) {
        if (vaccineRepo.findByProtectionEndDateBetween(startDate, endDate).isEmpty()) {
            throw new NotFoundException(Msg.NO_DATA_CRITERIA);
        }
        return vaccineRepo.findByProtectionEndDateBetween(startDate, endDate);
    }
}
