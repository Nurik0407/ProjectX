package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.models.Hospital;
import peaksoft.repository.DepartmentRepository;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository.repositoryImpl
 * 17.02.2023
 **/
@Repository
@Transactional
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @PersistenceContext
    private EntityManager en;

    @Override
    public List<Department> getAll() {
        try {

            return en.createQuery("select l from Department l", Department.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Department> getAll(Long id) {
        try {

            return en.createQuery("select  l from Department l join l.hospital h where h.id = :id", Department.class)
                    .setParameter("id", id).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public Department findById(Long id) {
        try {

            return en.find(Department.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Department department) {
        try {
            en.persist(department);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Department newDepartment) {
        try {
            Department department = en.find(Department.class, id);
            department.setName(newDepartment.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
            en.createQuery("delete  from Department  d where d.id=:id")
                    .setParameter("id",id).executeUpdate();
    }

    @Override
    public List<Department> getAllByHospitalId(Long id) {
        try {

            return en.createQuery("select l from Department l join l.hospital h where h.id=:id", Department.class)
                    .setParameter("id", id).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Department> getAllByDoctorId(Long id) {
        try {
            return en.createQuery("select l from Department l join l.doctors d where d.id=:id", Department.class)
                    .setParameter("id", id).getResultList();
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Doctor> getAllDocByDepId(Long departmentId) {
        try {
            return en.createQuery("""
                    select d from Doctor d join d.departments l where l.id=:id
                    """, Doctor.class).setParameter("id", departmentId).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

}
