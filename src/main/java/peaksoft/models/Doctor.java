package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.nullability.MaybeNull;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

/**
 * Zholdoshov Nuradil
 * peaksoft.models
 * 17.02.2023
 **/
@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_id_gen")
    @SequenceGenerator(name = "doctor_id_gen", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Name must by not empty!")
    @Size(max = 30, message = "Name cannot be longer than 30 characters!")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Surname must by not empty!")
    @Size(max = 30, message = "Surname cannot be longer than 30 characters!")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Fill in the field!")
    private String position;
    @NotEmpty(message = "Email must by not empty!")
    @Email(message = "Incorrect email!")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Fill in the field!")
    private String image;

    @ManyToMany(cascade = {REFRESH, MERGE, PERSIST, DETACH})
    private List<Department> departments = new ArrayList<>();
    @OneToMany(mappedBy = "doctor", cascade = {ALL})
    private List<Appointment> appointmentList;
    @ManyToOne(cascade = {PERSIST, REFRESH, DETACH, MERGE})
    private Hospital hospital;

    @Transient
    private List<Long> departmentIdes = new ArrayList<>();

    @Transient
    private Long hospitalId;

    public void addDepartment(Department department) {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    public Doctor(String firstName, String lastName, String position, String email, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.image = image;
    }
}
