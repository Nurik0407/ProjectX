package peaksoft.service;

import peaksoft.models.Patient;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface PatientService {
    List<Patient> getAll(Long id);

    Patient findById(Long id);

    void save(Patient patient,Long hospitalId);

    void update(Long id, Patient newPatient);

    void delete(Long id);
}
