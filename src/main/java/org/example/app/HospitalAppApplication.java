package org.example.app;

import org.example.app.entities.Patient;
import org.example.app.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class HospitalAppApplication implements CommandLineRunner {
    @Autowired
    PatientRepository PatientRepository;
    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //using builder
        /*Patient patient1 = Patient.builder().fullname("Darmian Antony").dateOfBirth(new Date()).gender("Male")
                .sick(false).score(30).build();
        PatientRepository.save(patient1);
        //using constructor with arguments
        Patient patient2 = new Patient(null, "Yelmaz adyemi", new Date(),
                "Male", true, 32);
        PatientRepository.save(patient2);*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

