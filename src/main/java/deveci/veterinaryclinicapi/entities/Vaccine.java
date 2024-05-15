package deveci.veterinaryclinicapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vaccine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "protection_start_date")
    private LocalDate protectionStartDate;

    @NotNull
    @Column(name = "protection_end_date")
    private LocalDate protectionEndDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private Animal animal;
}
