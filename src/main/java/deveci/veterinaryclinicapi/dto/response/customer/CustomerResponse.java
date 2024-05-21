package deveci.veterinaryclinicapi.dto.response.customer;

import deveci.veterinaryclinicapi.dto.response.animal.SimplifiedAnimalResponse;
import deveci.veterinaryclinicapi.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;
    private List<SimplifiedAnimalResponse> animalList;

}
