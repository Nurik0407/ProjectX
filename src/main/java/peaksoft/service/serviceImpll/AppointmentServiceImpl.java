package peaksoft.service.serviceImpll;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
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
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointment.getDate(), format);
            appointment.setLocalDate(date);
            appointment.setPatient(patientService.findById(appointment.getPatientId()));
            appointment.setDoctor(doctorService.findById(appointment.getDoctorId()));
            appointment.setDepartment(departmentService.findById(appointment.getDepartmentId()));
            hospital.addAppointment(appointment);
            appointmentRepository.save(appointment);
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

    @Override
    public void delete(Long id, Long hospitalId) {
            appointmentRepository.delete(id);
    }
}
