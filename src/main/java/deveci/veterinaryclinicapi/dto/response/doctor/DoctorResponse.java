package deveci.veterinaryclinicapi.dto.response.doctor;


import deveci.veterinaryclinicapi.dto.response.appointment.SimplifiedAppointmentResponse;
import deveci.veterinaryclinicapi.dto.response.availabledate.SimplifiedAvailableDateResponse;
import deveci.veterinaryclinicapi.entities.Appointment;
import deveci.veterinaryclinicapi.entities.AvailableDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;
    private List<SimplifiedAvailableDateResponse> dateList;
    private List<SimplifiedAppointmentResponse> appointmentList;

}
