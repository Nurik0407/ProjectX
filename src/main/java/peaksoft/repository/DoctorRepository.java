package peaksoft.repository;

import peaksoft.models.Doctor;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface DoctorRepository {

List<Doctor> getAll();
    List<Doctor> getAll(Long id);

    Doctor findById(Long id);

    void save(Doctor doctor);

    void update(Long id,Doctor doctor);

    void delete(Long id);
}
