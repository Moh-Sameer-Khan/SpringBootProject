package com.geekster.DoctorApp.controller;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.Doctor;
import com.geekster.DoctorApp.repository.IDoctorRepo;
import com.geekster.DoctorApp.repository.IPatientRepo;
import com.geekster.DoctorApp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    IDoctorRepo iDoctorRepo;

    @Autowired
    IPatientRepo iPatientRepo;

    //    Adding One Appointment at a time // Post/CREATE
    @PostMapping("appointment")
    public String scheduleAppointment(@RequestBody Appointment appointment) {
//        we are handling error message a/o us if doctor id, or patient id does not exist
//        id of doctor
        Long doctorId = appointment.getDoctor().getDoctorId();
        boolean isDoctorValid = iDoctorRepo.existsById(doctorId);

//        id of patient
        Long patientId = appointment.getPatient().getPatientId();
        boolean isPatientValid = iPatientRepo.existsById(patientId);
        if(isPatientValid && isDoctorValid){
            return appointmentService.scheduleAppointment(appointment);
        }else {
            throw new IllegalStateException("Appointment meta data is invalid!!");
        }
    }

    //   Get All Appointment list // Get/READ
    @GetMapping("appointments")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}
