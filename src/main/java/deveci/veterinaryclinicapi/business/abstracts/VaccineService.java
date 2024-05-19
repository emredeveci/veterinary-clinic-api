package deveci.veterinaryclinicapi.business.abstracts;

import deveci.veterinaryclinicapi.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface VaccineService {

    Vaccine save(Vaccine vaccine);

    Vaccine get(Long id);

    Page<Vaccine> cursor(int page, int pageSize);

    Vaccine update(Vaccine vaccine);

    boolean delete(Long id);

    List<Vaccine> getAnimalVaccineList(Long id);

    List<Vaccine> getFilterByStartAndEndDate(LocalDate startDate, LocalDate endDate);
}
