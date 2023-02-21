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
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.AppointmentService;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;

import java.util.Iterator;
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
    private final DoctorRepository doctorRepository;


    @Override
    public List<Department> getAll(Long id) {
        try {
            return departmentRepository.getAll(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


    @Override
    public Department findById(Long id) {
        try {
            return departmentRepository.findById(id);
        }catch (Exception e){
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
                    throw new RuntimeException(" Such a separation already exists");
                }
            }
            department.setHospital(hospitalRepository.findById(id));
            departmentRepository.save(department);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Department newDepartment) {
        try {
            for (Department department : departmentRepository.getAll(newDepartment.getHospitalId())) {
                if (department.getName().equals(newDepartment.getName())) {
                    throw new RuntimeException(" Such a separation already exists");
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

        try {
            Department department = departmentRepository.findById(id);
            List<Hospital> hospitals = hospitalRepository.getAllHospital();
            for (int z = 0; z < hospitals.size(); z++) {
                Hospital hospital = hospitals.get(z);
                List<Appointment> appointments = hospital.getAppointmentList();
                if (appointments != null) {
                    Iterator<Appointment> iterator = appointments.iterator();
                    while (iterator.hasNext()) {
                        Appointment appointment = iterator.next();
                        if (appointment.getDepartment() != null && appointment.getDepartment().getId().equals(id)) {
                            iterator.remove();
                            appointmentRepository.delete(appointment.getId());
                        }
                    }
                }
            }

            if (department.getDoctors() != null) {
                for (int i = 0; i < department.getDoctors().size(); i++) {
                    department.getDoctors().get(i).getDepartments().remove(department);
                }
            }
            if (department.getHospital() != null) {
                for (int i = 0; i < department.getHospital().getDepartmentList().size(); i++) {
                    department.getHospital().getDepartmentList().remove(department);
                }
            }
            departmentRepository.delete(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Department> getAllByHospitalId(Long id) {
        try {
            return departmentRepository.getAllByHospitalId(id);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


    @Override
    public List<Department> getAllByDoctorId(Long id) {
        try {
            return departmentRepository.getAllByDoctorId(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


}
