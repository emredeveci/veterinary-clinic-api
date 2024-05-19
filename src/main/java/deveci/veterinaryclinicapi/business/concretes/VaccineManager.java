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

@Service
public class VaccineManager implements VaccineService {

    private final VaccineRepo vaccineRepo;
    private final AnimalRepo animalRepo;

    public VaccineManager(VaccineRepo vaccineRepo, AnimalRepo animalRepo) {
        this.vaccineRepo = vaccineRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public Vaccine save(Vaccine vaccine) {
        if (animalRepo.findById(vaccine.getAnimal().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL_ID);
        }
        if (vaccineRepo.existsVaccineByCodeAndNameAndAnimalId(vaccine.getCode(), vaccine.getName(), vaccine.getAnimal().getId())) {
            if (vaccineRepo.findByEndDateAfterOrderByEndDate(vaccine.getProtectionStartDate()).isEmpty()) {
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
    public Vaccine get(Long id) {
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        this.get(vaccine.getId());
        if (animalRepo.findById(vaccine.getAnimal().getId()).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL_ID);
        }
        if (vaccineRepo.existsVaccineByCodeAndNameAndAnimalId(vaccine.getCode(), vaccine.getName(), vaccine.getAnimal().getId())) {
            if (vaccineRepo.findByEndDateAfterOrderByEndDate(vaccine.getProtectionStartDate()).isEmpty()) {
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
        this.vaccineRepo.delete(this.get(id));
        return true;
    }

    @Override
    public List<Vaccine> getAnimalVaccineList(Long id) {
        if (vaccineRepo.findByAnimalId(id).isEmpty()) {
            throw new NotFoundException(Msg.NO_SUCH_ANIMAL_ID);
        }
        return vaccineRepo.findByAnimalId(id);
    }

    @Override
    public List<Vaccine> getFilterByStartAndEndDate(LocalDate startDate, LocalDate endDate) {
        if (vaccineRepo.findByEndDateBetween(startDate, endDate).isEmpty()) {
            throw new NotFoundException(Msg.NO_DATA_CRITERIA);
        }
        return vaccineRepo.findByEndDateBetween(startDate, endDate);
    }
}
