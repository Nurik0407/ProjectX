package peaksoft.service.serviceImpll;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.models.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;

    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Department> getAll(Long id) {
        try {
            return departmentRepository.getAll(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


    @Override
    public Department findById(Long id) {
        try {
            return departmentRepository.findById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Transactional
    @Override
    public void save(Department department, Long id) {
        try {
            for (Department depart : departmentRepository.getAll(id)) {
                if (depart.getName().equals(department.getName())) {
                    throw new RuntimeException();
                }
            }
            department.setHospital(hospitalRepository.findById(id));
            departmentRepository.save(department);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Long id, Department newDepartment) {
        try {
            for (Department department : departmentRepository.getAll(departmentRepository.findById(id).getHospital().getId())) {
                if (department.getName().equals(newDepartment.getName()) && !department.getId().equals(id)) {
                    throw new RuntimeException();
                }
            }
            departmentRepository.update(id, newDepartment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Department department = departmentRepository.findById(id);
        List<Hospital> hospitals = hospitalRepository.getAllHospital();

        for (Hospital hospital : hospitals) {
            List<Appointment> appointments = hospital.getAppointmentList();
            if (appointments != null) {
                List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
                appointmentList.forEach(s -> appointmentRepository.delete(s.getId()));
            }
        }

        List<Department> departments = department.getHospital().getDepartmentList();
        departments.removeIf(s -> s.getId().equals(id));

        departmentRepository.delete(id);
    }

    @Override
    public List<Department> getAllByHospitalId(Long id) {
        try {
            return departmentRepository.getAllByHospitalId(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Doctor> getAllDoByDepId(Long departmentId) {
        try {

            return departmentRepository.getAllDocByDepId(departmentId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


}
