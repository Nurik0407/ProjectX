package peaksoft.service;

import peaksoft.models.Department;
import peaksoft.models.Doctor;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface DepartmentService {

    List<Department> getAll(Long id);

    Department findById(Long id);

    void save(Department department,Long id);

    void update(Long id,Department newDepartment);

    void delete(Long id);
    List<Department> getAllByHospitalId(Long id);

    List<Department> getAllByDoctorId(Long id);

    List<Doctor> getAllDoByDepId(Long departmentId);
}
