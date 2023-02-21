package peaksoft.service;

import peaksoft.models.Department;
import peaksoft.models.Hospital;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface HospitalService {
    List<Hospital> getAllHospital();
    void saveHospital(Hospital hospital);

    Hospital findById(Long id);

    void update(Long id, Hospital newHospital);

    void delete(Long id);

    List<Department> getAllDepartmentByHospitalId(Long id);


    List<Hospital> getAllHospital(String keyWord);
}
