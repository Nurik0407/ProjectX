package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import peaksoft.models.Doctor;
import peaksoft.repository.DoctorRepository;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository.repositoryImpl
 * 17.02.2023
 **/
@Repository
@Transactional
public class DoctorRepositoryImpl implements DoctorRepository {
    @PersistenceContext
    private EntityManager en;


    @Override
    public List<Doctor> getAll() {
        try {
            return en.createQuery("select l from Doctor l ", Doctor.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Doctor> getAll(Long id) {
        try {
            return en.createQuery("select l from Doctor l join l.hospital h where h.id = :id", Doctor.class)
                    .setParameter("id", id).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public Doctor findById(Long id) {
        try {
            return en.find(Doctor.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Doctor doctor) {
        try {
            en.merge(doctor);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Doctor doctor) {
        try {
            en.merge(doctor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        en.createQuery(" delete  from  Doctor d where d.id=:id").
                setParameter("id", id).executeUpdate();
    }


}
