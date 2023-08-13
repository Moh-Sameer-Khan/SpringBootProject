package com.geekster.DoctorApp.controller;

import com.geekster.DoctorApp.model.Doctor;
import com.geekster.DoctorApp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoctorController {
    @Autowired
    DoctorService doctorService;

//    Adding One Doctor at a time // Post/CREATE
    @PostMapping("doctor")
    public String addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

//   Get All doctors list // Get/READ
    @GetMapping("doctors")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}
