package deveci.veterinaryclinicapi.dto.request.doctor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DoctorUpdateRequest {

    @Positive(message = "Customer ID needs to be a positive number.")
    @NotNull(message = "Customer ID cannot be null.")
    private Long id;

    @NotNull(message = "Name cannot be empty or null.")
    @NotEmpty(message = "Name cannot be empty or null.")
    private String name;

    @NotNull(message = "Phone cannot be empty or null.")
    @NotEmpty(message = "Phone cannot be empty or null.")
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
}
