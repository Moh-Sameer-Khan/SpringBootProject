package com.geekster.DoctorApp.service;

import com.geekster.DoctorApp.model.Appointment;
import com.geekster.DoctorApp.model.AuthenticationToken;
import com.geekster.DoctorApp.model.Patient;
import com.geekster.DoctorApp.model.dataToObject.SignInInput;
import com.geekster.DoctorApp.model.dataToObject.SignUpOutput;
import com.geekster.DoctorApp.repository.IAppointmentRepo;
import com.geekster.DoctorApp.repository.IAuthTokenRepo;
import com.geekster.DoctorApp.repository.IPatientRepo;
import com.geekster.DoctorApp.service.utility.emailUtility.EmailHandler;
import com.geekster.DoctorApp.service.utility.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    IPatientRepo iPatientRepo;

    @Autowired
    IAuthTokenRepo iAuthTokenRepo;

    @Autowired
    IAppointmentRepo iAppointmentRepo;
    public SignUpOutput signUpPatient(Patient patient) {
        Boolean signUpStatus = true;
        String signUpStatusMessage = null;
//        get  the email of patient who we are passing data
        String newEmail = patient.getPatientEmail();
        if(newEmail == null) {
            signUpStatusMessage = "Invalid Email!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
//        check if this patient email already exists??
        Patient existingPatient = iPatientRepo.findFirstByPatientEmail(newEmail);

        if(existingPatient != null) {
            signUpStatusMessage = "Email Already Registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
        try {
            //        hash the Password : Encrypt the password
            String encryptPassword= PasswordEncrypter.encryptPassword(patient.getPatientPassword());

//        save the patient with the new encrypted password
            patient.setPatientPassword(encryptPassword);
            iPatientRepo.save(patient);

//        return now
            return new SignUpOutput(signUpStatus, "Patient Registered Successfully!!!");

        } catch (Exception ee) {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }


    }

    public List<Patient> getAllPatients() {
        return iPatientRepo.findAll();
    }

    public String signInPatient(SignInInput signInInput) {

        String signInStatusMessage = null;
        //        get  the email of SignInInput who we are passing data
        String signInEmail = signInInput.getEmail();
        if(signInEmail == null) {
            signInStatusMessage = "Invalid Sign-in Email";
            return  signInStatusMessage;
        }

        //        check if this patient email already exists??
        Patient existingPatient = iPatientRepo.findFirstByPatientEmail(signInEmail);

        if(existingPatient == null) {
            signInStatusMessage = "Email Not Registered!!!";
            return signInStatusMessage;
        }


        try {
            //        hash the Password : Encrypt the password
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());

//            get the existing password from patient
            String existingPassword = existingPatient.getPatientPassword();
//            Match both password for sign in
            if(encryptedPassword.equals(encryptedPassword)) {
//                Session should be created since password matched and user id is valid
                AuthenticationToken authenticationToken = new AuthenticationToken(existingPatient);
//                save this new token in the database
                iAuthTokenRepo.save(authenticationToken);

//                sending message/otp for signing on email
//                EmailHandler.sendEmail("mskhanm1819@gmail.com", "Email testing", authenticationToken.getTokenValue());
                EmailHandler.sendEmail(existingPatient.getPatientEmail(), "Email testing", authenticationToken.getTokenValue());
                return "Token sent to your Email";
            }else {
                signInStatusMessage = "Invalid Credentials!!!";
                return signInStatusMessage;
            }

        } catch (Exception ee) {
            signInStatusMessage = "Internal error occurred during SIGN IN";
            return signInStatusMessage;
        }
    }

    public void scheduleAppointment(Appointment appointment) {
        iAppointmentRepo.save(appointment);
    }
}
