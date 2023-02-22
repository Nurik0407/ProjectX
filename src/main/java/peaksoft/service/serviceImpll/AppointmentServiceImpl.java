package peaksoft.service.serviceImpll;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Department;
import peaksoft.models.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.AppointmentService;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.PatientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @Override
    public List<Appointment> getAll(Long id) {
        try {
            return appointmentRepository.getAll(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public Appointment findById(Long id) {
        try {
            return appointmentRepository.findById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Transactional
    @Override
    public void save(Appointment appointment, Long hospitalId) {
        try {
            Hospital hospital = hospitalRepository.findById(hospitalId);
            Appointment newAppointment = new Appointment();
            newAppointment.setId(appointment.getId());
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointment.getDate(), format);
            newAppointment.setLocalDate(date);
            newAppointment.setPatient(patientService.findById(appointment.getPatientId()));
            newAppointment.setDoctor(doctorService.findById(appointment.getDoctorId()));
            newAppointment.setDepartment(departmentService.findById(appointment.getDepartmentId()));
            hospital.addAppointment(newAppointment);
            appointmentRepository.save(newAppointment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        try {
            appointmentRepository.update(id, newAppointment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(Long id, Long hospitalId) {
        try {
            List<Appointment> appointments = hospitalRepository.findById(hospitalId).getAppointmentList();
            if (appointments != null) {
                for (int i = 0; i < appointments.size(); i++) {
                    if (appointments.get(i).getId().equals(id)) {
                        appointments.remove(appointmentRepository.findById(id));
                    }
                }
            }
            appointmentRepository.delete(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
