package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.model.Doctor;
import com.geekster.DoctorApp.repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    IDoctorRepo iDoctorRepo;

    public String addDoctor(Doctor doctor) {
        iDoctorRepo.save(doctor);
        return "Doctor Added Successfully!!!";
    }

    public List<Doctor> getAllDoctors() {
        return iDoctorRepo.findAll();
    }
}
