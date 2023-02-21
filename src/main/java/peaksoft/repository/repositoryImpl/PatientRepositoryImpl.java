package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import peaksoft.models.Patient;
import peaksoft.repository.PatientRepository;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository.repositoryImpl
 * 17.02.2023
 **/
@Repository
@Transactional
public class PatientRepositoryImpl implements PatientRepository {
    @PersistenceContext
    private EntityManager en;

    @Override
    public List<Patient> getAll(Long id) {
        try {
            return en.createQuery("select l from Patient l join l.hospital h where h.id=:id", Patient.class)
                    .setParameter("id", id).getResultList();
        } catch (HttpClientErrorException.NotFound f) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Patient> getAll() {
        try {
            return en.createQuery("select l from Patient l", Patient.class).getResultList();
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException();
        }
    }


    @Override
    public Patient findById(Long id) {
        try {

            return en.find(Patient.class, id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Patient patient) {
        try {
            en.persist(patient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Patient newPatient) {
        try {
            en.merge(newPatient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {

            Patient patient = en.find(Patient.class, id);
            patient.getHospital().getPatientList().remove(patient);
            patient.setHospital(null);
            en.remove(en.find(Patient.class, id));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
