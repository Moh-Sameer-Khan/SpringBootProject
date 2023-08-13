package com.geekster.DoctorApp.repository;

import com.geekster.DoctorApp.model.Admin;
import com.geekster.DoctorApp.model.AuthenticationToken;
import com.geekster.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken, Long> {

    AuthenticationToken findFirstByTokenValue(String authTokenValue);
}
