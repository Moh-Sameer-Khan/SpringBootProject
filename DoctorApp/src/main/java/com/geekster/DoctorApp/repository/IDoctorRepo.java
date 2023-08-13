package com.geekster.DoctorApp.repository;

import com.geekster.DoctorApp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepo extends JpaRepository<Doctor, Long> {
}
