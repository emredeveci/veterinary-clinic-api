package deveci.veterinaryclinicapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Animal> animalList;
}
