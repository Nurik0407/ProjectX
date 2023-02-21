package peaksoft.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.annotations.NotFound;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import peaksoft.models.*;
import peaksoft.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository.repositoryImpl
 * 17.02.2023
 **/
@Repository
@Transactional
public class AppointmentRepositoryImpl implements AppointmentRepository {
    @PersistenceContext
    private EntityManager en;

    @Override
    public List<Appointment> getAll(Long id) {
        try {

            return en.createQuery("select a from Hospital h join h.appointmentList a where h.id=:id order by a.localDate ", Appointment.class)
                    .setParameter("id", id).getResultList();
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public Appointment findById(Long id) {
        try {

            return en.find(Appointment.class, id);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Appointment appointment) {
        try {
            en.merge(appointment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        try {
            Appointment appointment = en.find(Appointment.class,id);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(newAppointment.getDate(), format);
            appointment.setLocalDate(date);
            appointment.setPatient(en.find(Patient.class,newAppointment.getPatientId()));
            appointment.setDoctor(en.find(Doctor.class,newAppointment.getDoctorId()));
            appointment.setDepartment(en.find(Department.class,newAppointment.getDepartmentId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            en.remove(en.find(Appointment.class, id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointment> getAll() {
        try {
            return en.createQuery("select l from Appointment  l order by l.localDate", Appointment.class)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


}
