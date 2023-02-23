package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.models.Department;
import peaksoft.models.Hospital;
import peaksoft.repository.HospitalRepository;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository.repositoryImpl
 * 17.02.2023
 **/
@Repository
@Transactional
public class HospitalRepositoryImpl implements HospitalRepository {

    @PersistenceContext
    private EntityManager en;

    @Override
    public List<Hospital> getAllHospital() {
        try {

            return en.createQuery("select h from Hospital h", Hospital.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void saveHospital(Hospital hospital) {
        try {
            en.persist(hospital);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Hospital findById(Long id) {
        try {

            return en.find(Hospital.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        Hospital oldHospital = en.find(Hospital.class, id);
        oldHospital.setId(newHospital.getId());
        oldHospital.setName(newHospital.getName());
        oldHospital.setAddress(newHospital.getAddress());
        oldHospital.setImage(newHospital.getImage());
    }

    @Override
    public void delete(Long id) {
        en.remove(en.find(Hospital.class, id));
    }

    @Override
    public List<Department> getAllDepartmentByHospitalId(Long id) {
        List<Department> departments;
        departments = en.createQuery("select  d from Department d join Hospital h on h.id = d.id where h.id=:id", Department.class)
                .setParameter("id", id).getResultList();
        return departments;
    }

    @Override
    public List<Hospital> getAllHospital(String keyWord) {
        try {

            return en.createQuery("""
                    select h from Hospital h where h.name ILIKE (:keyWord)
                    """, Hospital.class).setParameter("keyWord", "%" + keyWord + "%").getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
