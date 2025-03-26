package org.example.app.web;

import lombok.AllArgsConstructor;
import org.example.app.entities.Patient;
import org.example.app.repositories.PatientRepository;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public String index(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "4") int size,
                        @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<Patient> pagePatients = patientRepository.findByFullnameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("nbPages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("patients", pagePatients.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        return "patients";
    }
}
