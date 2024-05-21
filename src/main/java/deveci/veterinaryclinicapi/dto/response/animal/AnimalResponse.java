package deveci.veterinaryclinicapi.dto.response.animal;

import deveci.veterinaryclinicapi.dto.response.appointment.SimplifiedAppointmentResponse;
import deveci.veterinaryclinicapi.dto.response.customer.SimplifiedCustomerResponse;
import deveci.veterinaryclinicapi.dto.response.vaccine.SimplifiedVaccineResponse;
import deveci.veterinaryclinicapi.entities.Appointment;
import deveci.veterinaryclinicapi.entities.Customer;
import deveci.veterinaryclinicapi.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponse {

    private Long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String color;
    private LocalDate dateOfBirth;
    private SimplifiedCustomerResponse customer;
    private List<SimplifiedVaccineResponse> vaccineList;
    private List<SimplifiedAppointmentResponse> appointmentList;
}
