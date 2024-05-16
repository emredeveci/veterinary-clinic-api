package deveci.veterinaryclinicapi.dto.request.customer;

import deveci.veterinaryclinicapi.entities.Animal;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String name;

    @NotNull(message = "Phone cannot be empty or null.")
    @NotEmpty(message = "Phone cannot be empty or .")
    private String phone;

    @NotNull(message = "Email cannot be empty or null.")
    @NotEmpty(message = "Email cannot be empty or null.")
    private String email;

    @NotNull(message = "Address cannot be empty or null.")
    @NotEmpty(message = "Address cannot be empty or null.")
    private String address;

    @NotNull(message = "City cannot be empty or null.")
    @NotEmpty(message = "City cannot be empty or null.")
    private String city;

//    @NotNull(message = "Pet information cannot be empty or null.")
//    @NotEmpty(message = "Pet information cannot be empty or null.")
//    private List<Animal> animalList;
}
