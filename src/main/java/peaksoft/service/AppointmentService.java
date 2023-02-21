package peaksoft.service;

import peaksoft.models.Appointment;
import peaksoft.models.Department;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface AppointmentService {


    List<Appointment> getAll(Long id);

    Appointment findById(Long id);

    void save(Appointment appointment,Long hospitalId);

    void update(Long id, Appointment newAppointment);

    void delete(Long id,Long hospitalId);
}
