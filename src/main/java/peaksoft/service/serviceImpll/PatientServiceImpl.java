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
    public void save(Patient patient) {
        try {
            for (Patient patient1 : patientRepository.getAll()) {
                if (patient1.getPhoneNumber().equals(patient.getPhoneNumber())){
                    throw new RuntimeException("This phone number already exists");
                }
            }
            if (!patient.getPhoneNumber().startsWith("+996")){
                throw new RuntimeException("Number must start with +996");
            }
            patient.setHospital(hospitalRepository.findById(patient.getHospitalId()));
            patientRepository.save(patient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void update(Long id, Patient newPatient) {
        try {
            Patient patient = patientRepository.findById(id);
            patient.setFirstName(newPatient.getFirstName());
            patient.setLastName(newPatient.getLastName());
            patient.setEmail(newPatient.getEmail());
            patient.setGender(newPatient.getGender());
            for (Patient patient1 : patientRepository.getAll()) {
                if (patient1.getPhoneNumber().equals(newPatient.getPhoneNumber())){
                    throw new RuntimeException("This phone number already exists");
                }
            }
            if (!newPatient.getPhoneNumber().startsWith("+996")){
                throw new RuntimeException("Number must start with +996");
            }
            patient.setPhoneNumber(newPatient.getPhoneNumber());
            patient.setHospital(hospitalRepository.findById(newPatient.getHospitalId()));
            patientRepository.update(id, patient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
