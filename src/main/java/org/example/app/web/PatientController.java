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
                        @RequestParam(value = "size", defaultValue = "4") int size) {
        Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("nbPages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("patients", pagePatients.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "patients";
    }
    @PostMapping("/patients/changeSize")
    public String changePageSize(@RequestParam("size") int size,
            @RequestParam("currentPage") int currentPage) {
        return "redirect:/patients?page=" + currentPage + "&size=" + size;
    }
}
