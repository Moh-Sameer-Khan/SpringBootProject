package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.repository.IAppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    IAppointmentRepo iAppointmentRepo;

    public String scheduleAppointment(Appointment appointment) {
        iAppointmentRepo.save(appointment);
        return "Appointment Added Successfully";
    }

    public List<Appointment> getAllAppointments() {
        return iAppointmentRepo.findAll();
    }
}
