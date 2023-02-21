package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

/**
 * Zholdoshov Nuradil
 * peaksoft.models
 * 17.02.2023
 **/
@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_gen")
    @SequenceGenerator(name = "department_id_gen", allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Department must not be empty!")
    @Size(min = 2,max = 30,message = "Length cannot be longer than 30 characters!")
    private String name;
    @ManyToMany(mappedBy = "departments", cascade = {PERSIST, MERGE, DETACH, REFRESH})
    private List<Doctor> doctors;
    @ManyToOne(cascade = {REFRESH, MERGE, PERSIST, DETACH})
    private Hospital hospital;
    @Transient
    private Long hospitalId;

    public Department(String name) {
        this.name = name;
    }

}
