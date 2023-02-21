package peaksoft.repository;

import peaksoft.models.Patient;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface PatientRepository {

    List<Patient> getAll(Long id);
    List<Patient> getAll();

    Patient findById(Long id);

    void save(Patient patient);

    void update(Long id, Patient newPatient);

    void delete(Long id);
}
