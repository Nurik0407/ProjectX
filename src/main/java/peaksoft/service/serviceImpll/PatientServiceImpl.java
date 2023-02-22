package peaksoft.service.serviceImpll;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Hospital;
import peaksoft.models.Patient;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.repository.PatientRepository;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;

import java.util.Iterator;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Patient> getAll(Long id) {
        return patientRepository.getAll(id);
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Patient patient, Long hospitalId) {
        try {
            patient.setHospital(hospitalRepository.findById(hospitalId));
            if (patientRepository.getAll() != null) {
                for (Patient pat : patientRepository.getAll()) {
                    if (pat.getPhoneNumber().equals(patient.getPhoneNumber())) {
                        throw new RuntimeException();
                    }
                }
            }
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    @Override
    public void update(Long id, Patient newPatient) {
        try {
            Patient patient = patientRepository.findById(id);

            for (Patient pat : patientRepository.getAll()) {
                if (pat.getPhoneNumber().equals(newPatient.getPhoneNumber()) && !pat.getId().equals(id)) {
                    throw new RuntimeException();
                }
            }

            patient.setFirstName(newPatient.getFirstName());
            patient.setLastName(newPatient.getLastName());
            patient.setEmail(newPatient.getEmail());
            patient.setGender(newPatient.getGender());
            patient.setPhoneNumber(newPatient.getPhoneNumber());
            patientRepository.update(id, patient);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            Patient patient = patientRepository.findById(id);

            List<Patient> patients = patient.getHospital().getPatientList();
            if (patients != null) {
                for (int i = 0; i < patients.size(); i++) {
                    if (patients.get(i).getId().equals(id)) {
                        patients.remove(patient);
                    }
                }
            }


            List<Hospital> hospitals = hospitalRepository.getAllHospital();
            for (int z = 0; z < hospitals.size(); z++) {
                List<Appointment> appointments = hospitals.get(z).getAppointmentList();
                if (appointments != null) {
                    Iterator<Appointment> iterator = appointments.listIterator();
                    while (iterator.hasNext()) {
                        Appointment appointment = iterator.next();
                        if (appointment.getPatient() != null && appointment.getPatient().getId().equals(id)) {
                            iterator.remove();
                            appointmentRepository.delete(appointment.getId());
                        }
                    }
                }
            }
            patientRepository.delete(patient.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
