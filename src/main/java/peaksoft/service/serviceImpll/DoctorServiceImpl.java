package peaksoft.service.serviceImpll;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DoctorService;

import java.util.ArrayList;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {


    private final DoctorRepository doctorRepository;

    private final HospitalRepository hospitalRepository;

    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Doctor> getAll(Long id) {
        try {
            return doctorRepository.getAll(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public Doctor findById(Long id) {
        try {
            return doctorRepository.findById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


    @Override
    public void save(Doctor doctor, Long id) {
        try {
            Doctor newDoctor = new Doctor();
            newDoctor.setId(doctor.getId());
            newDoctor.setFirstName(doctor.getFirstName());
            newDoctor.setLastName(doctor.getLastName());
            newDoctor.setImage(doctor.getImage());
            for (Doctor doctor1 : doctorRepository.getAll()) {
                if (doctor1.getEmail().equals(doctor.getEmail())) {
                    throw new RuntimeException("Email already registered!");
                }
            }
            if (!doctor.getEmail().contains("@")) {
                throw new RuntimeException("Incorrect email!");
            }

            newDoctor.setEmail(doctor.getEmail());
            newDoctor.setPosition(doctor.getPosition());
            newDoctor.setHospital(hospitalRepository.findById(id));
            doctor.getDepartmentIdes().forEach(s -> newDoctor.addDepartment(departmentRepository.findById(s)));
            doctorRepository.save(newDoctor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Doctor doctor) {
        try {
            Doctor newDoctor = doctorRepository.findById(id);
            newDoctor.setFirstName(doctor.getFirstName());
            newDoctor.setLastName(doctor.getLastName());
            newDoctor.setImage(doctor.getImage());
            for (Doctor doctor1 : doctorRepository.getAll()) {
                if (doctor1.getEmail().equals(doctor.getEmail())) {
                    throw new RuntimeException("Email already registered!");
                }
            }
            if (!doctor.getEmail().contains("@")) {
                throw new RuntimeException("Incorrect email!");
            }
            newDoctor.setEmail(doctor.getEmail());
            newDoctor.setPosition(doctor.getPosition());
            newDoctor.setHospital(hospitalRepository.findById(doctor.getHospitalId()));
            newDoctor.setDepartments(null);
            doctor.getDepartmentIdes().forEach(s -> newDoctor.addDepartment(departmentRepository.findById(s)));
            doctorRepository.update(id, newDoctor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            Doctor doctor = doctorRepository.findById(id);
            for (int i = 0; i < hospitalRepository.getAllHospital().size(); i++) {
                for (int j = 0; j < hospitalRepository.getAllHospital().get(i).getAppointmentList().size(); j++) {
                    for (int l = 0; l < doctor.getAppointmentList().size(); l++) {
                        if (hospitalRepository.getAllHospital().get(i).getAppointmentList().get(j).getId().equals(doctor.getAppointmentList().get(l).getId())) {
                            hospitalRepository.getAllHospital().get(i).getAppointmentList().remove(doctor.getAppointmentList().get(l));
                            appointmentRepository.delete(doctor.getAppointmentList().get(l).getId());
                        }
                    }
                }
            }

            if (doctor.getDepartments() != null) {
                doctor.setDepartments(null);
            }
            for (int i = 0; i < hospitalRepository.getAllHospital().size(); i++) {
                if (hospitalRepository.getAllHospital().get(i).getDoctorList() != null) {
                    for (int j = 0; j < hospitalRepository.getAllHospital().get(i).getDoctorList().size(); j++) {
                        if (hospitalRepository.getAllHospital().get(i).getDoctorList().get(j).equals(doctor)) {
                            hospitalRepository.getAllHospital().get(i).getDoctorList().remove(doctor);
                        }
                    }
                }
            }
            doctorRepository.delete(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
