package org.example.app.web;

import lombok.AllArgsConstructor;
import org.example.app.entities.Patient;
import org.example.app.repositories.PatientRepository;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public String index(Model model, RedirectAttributes red,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "4") int size,
                        @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<Patient> pagePatients = patientRepository.findByFullnameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("nbPages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("patients", pagePatients.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        red.addAttribute("success", null);
        red.addAttribute("error", null);
        return "patients";
    }

    @PostMapping("/patients/delete/{id}")
    public String delete(RedirectAttributes red, @PathVariable long id) {
        Patient p = patientRepository.findById(id).orElse(null);
        if(p == null) red.addFlashAttribute("error", "Error deleting Patient !");
        else {
            patientRepository.delete(p);
            red.addFlashAttribute("success", "Patient deleted successfully !");
        }
        return "redirect:/patients";
    }
}
