package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.repository.IAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    IAdminRepo iAdminRepo;
}
