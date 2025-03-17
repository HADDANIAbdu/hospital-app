package org.example.app.web;

import lombok.AllArgsConstructor;
import org.example.app.entities.Patient;
import org.example.app.repositories.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public String index(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patients";
    }
}
