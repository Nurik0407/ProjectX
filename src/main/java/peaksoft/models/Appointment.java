package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

/**
 * Zholdoshov Nuradil
 * peaksoft.models
 * 17.02.2023
 **/

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_id_gen")
    @SequenceGenerator(name = "appointment_id_gen", allocationSize = 1)
    private Long id;
    private LocalDate localDate;
    @ManyToOne(cascade = {MERGE, PERSIST, DETACH, REFRESH})
    private Patient patient;

    @ManyToOne(cascade = {REFRESH, PERSIST, MERGE, DETACH})
    private Doctor doctor;
    @ManyToOne(cascade = {REFRESH, PERSIST, MERGE, DETACH})
    private Department department;

    @Transient
    private Long patientId;
    @Transient
    private Long doctorId;
    @Transient
    private Long departmentId;
    @Transient
    private String date;

    public Appointment(LocalDate localDate) {
        this.localDate = localDate;
    }
}
