package peaksoft.service.serviceImpll;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Doctor;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DoctorService;

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
            doctor.setHospital(hospitalRepository.findById(id));
            doctor.getDepartmentIdes().forEach(s -> doctor.addDepartment(departmentRepository.findById(s)));
            doctorRepository.save(doctor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Doctor doctor) {
        try {
            Doctor oldDoctor = doctorRepository.findById(id);
            oldDoctor.setFirstName(doctor.getFirstName());
            oldDoctor.setLastName(doctor.getLastName());
            oldDoctor.setImage(doctor.getImage());
            oldDoctor.setEmail(doctor.getEmail());
            oldDoctor.setPosition(doctor.getPosition());
            oldDoctor.setDepartments(null);
            doctor.getDepartmentIdes().forEach(s -> oldDoctor.addDepartment(departmentRepository.findById(s)));
            doctorRepository.update(id, oldDoctor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Doctor doctor = doctorRepository.findById(id);
        List<Appointment> appointments = doctor.getHospital().getAppointmentList();

        if (appointments != null) {
            List<Appointment> appointmentList = appointments.stream().filter(a -> a.getDoctor().getId().equals(id)).toList();
            appointmentList.forEach(s -> appointmentRepository.delete(s.getId()));
        }

        List<Doctor> doctors = doctor.getHospital().getDoctorList();
        doctors.removeIf(s -> s.getId().equals(id));

        doctorRepository.delete(id);

    }
}
