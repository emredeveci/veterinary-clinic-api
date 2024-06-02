package deveci.veterinaryclinicapi.service.abstracts;

import deveci.veterinaryclinicapi.entities.AvailableDate;
import org.springframework.data.domain.Page;

public interface AvailableDateService {
    AvailableDate save(AvailableDate availableDate);

    AvailableDate get(Long id);

    Page<AvailableDate> cursor(int page, int pageSize);

    AvailableDate update(AvailableDate availableDate);

    boolean delete(Long id);
}
