package deveci.veterinaryclinicapi.dto.response.vaccine;


import deveci.veterinaryclinicapi.dto.response.animal.SimplifiedAnimalResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineResponse {

    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionEndDate;
    private SimplifiedAnimalResponse animal;
}
