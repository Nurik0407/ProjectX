package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import peaksoft.enums.Gender;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

/**
 * Zholdoshov Nuradil
 * peaksoft.models
 * 17.02.2023
 **/
@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_id_gen")
    @SequenceGenerator(name = "patient_id_gen", allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Name must not be empty!")
    @Size(max = 30, message = "Name cannot be longer than 30 characters!")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Surname must by not empty!")
    @Size(max = 30, message = "Surname cannot be longer than 30 characters!")
    @Column(name = "last_name")
    private String lastName;
    @Pattern(regexp = "^\\+996\\d{9}$", message = "Phone number must start with +996 and contain 12 digits")
    @Column(name = "phone_number")
    private String phoneNumber;
    private Gender gender;
    @NotEmpty(message = "Email must by not empty!")
    @Email(message = "Incorrect email!")
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = {PERSIST, MERGE, DETACH, REFRESH})
    private Hospital hospital;
    @OneToMany(mappedBy = "patient", cascade = ALL)
    private List<Appointment> appointments;

    @Transient
    private Long hospitalId;

    public Patient(String firstName, String lastName, String phoneNumber, Gender gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
    }
}
