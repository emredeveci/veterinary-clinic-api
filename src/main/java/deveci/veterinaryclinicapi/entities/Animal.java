package deveci.veterinaryclinicapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "species")
    private String species;

    @NotNull
    @Column(name = "breed")
    private String breed;

    @NotNull
    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private List<Vaccine> vaccineList;

    @OneToMany(mappedBy = "animal")
    private List<Appointment> appointmentList;
}
