package peaksoft.service.serviceImpll;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Department;
import peaksoft.models.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> getAllHospital() {
        try {
            return hospitalRepository.getAllHospital();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void saveHospital(Hospital hospital) {
        try {
            hospitalRepository.saveHospital(hospital);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Hospital findById(Long id) {
        try {
            return hospitalRepository.findById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        try {
            hospitalRepository.update(id, newHospital);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            hospitalRepository.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Department> getAllDepartmentByHospitalId(Long id) {
        try {
            return hospitalRepository.getAllDepartmentByHospitalId(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospital(String keyWord) {
        try {
            if (keyWord != null) {
                return hospitalRepository.getAllHospital(keyWord);
            } else {
                return hospitalRepository.getAllHospital();
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
