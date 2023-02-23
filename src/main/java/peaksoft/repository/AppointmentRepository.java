package peaksoft.repository;

import peaksoft.models.Appointment;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface AppointmentRepository {

    List<Appointment> getAll(Long id);

    Appointment findById(Long id);

    void save(Appointment appointment);

    void update(Long id,Appointment newAppointment);

    void delete(Long id);


    List<Appointment> getAll();
}

