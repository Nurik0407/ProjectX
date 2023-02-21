package peaksoft.repository;

import peaksoft.models.Department;
import peaksoft.models.Doctor;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface DepartmentRepository {

    List<Department> getAll();
    List<Department> getAll(Long id);

    Department findById(Long id);

    void save(Department department);

    void update(Long id,Department newDepartment);

    void delete(Long id);

    List<Department> getAllByHospitalId(Long id);
    List<Department> getAllByDoctorId(Long id);

    List<Doctor> getAllDocByDepId(Long departmentId);
}

