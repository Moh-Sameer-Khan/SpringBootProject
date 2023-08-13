package com.geekster.DoctorApp.controller;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.Patient;
import com.geekster.DoctorApp.model.dataToObject.SignInInput;
import com.geekster.DoctorApp.model.dataToObject.SignUpOutput;
import com.geekster.DoctorApp.service.AuthenticationService;
import com.geekster.DoctorApp.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PatientController {
    @Autowired
    PatientService patientService;

    @Autowired
    AuthenticationService authenticationService;
    //    Adding One Patient at a time // Post/CREATE // SIGN-UP with hashing password
    @PostMapping("patient/signUp")
    public SignUpOutput signUpPatient(@RequestBody @Valid Patient patient) {
        return patientService.signUpPatient(patient);
    }

    //    Adding One Patient at a time // Post/CREATE // SIGN-IN
    @PostMapping("patient/signIn")
    public String signInPatient(@RequestBody @Valid SignInInput signInInput) {
        return patientService.signInPatient(signInInput);
    }



    //   Get All doctors list // Get/READ
    @GetMapping("patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    //  Schedule Appointment at a time // Post/CREATE // Schedule
    @PostMapping("patients/appointment/schedule")
    public String scheduleAppointment(@RequestBody Appointment appointment, @RequestParam String email, @RequestParam String token) {
        boolean authenticateResult = authenticationService.authenticate(email, token);
        if(authenticateResult) {
            patientService.scheduleAppointment(appointment);
            return "Appointment Scheduled";
        }else {
            return "Appointment Scheduling failed because invalid authentication";
        }

    }
}
