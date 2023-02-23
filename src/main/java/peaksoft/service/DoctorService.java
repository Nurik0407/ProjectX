package peaksoft.service;

import peaksoft.models.Doctor;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface DoctorService {
    List<Doctor> getAll(Long id);

    Doctor findById(Long id);

    void save(Doctor doctor,Long id);

    void update(Long id,Doctor doctor);

    void delete(Long id);
}
