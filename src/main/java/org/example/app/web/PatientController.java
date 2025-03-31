package org.example.app.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.app.entities.Patient;
import org.example.app.repositories.PatientRepository;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/patients/delete")
    public String delete(RedirectAttributes red, @RequestParam Long id, @RequestParam String keyword, @RequestParam int page,
                            @RequestParam int size) {
        Patient p = patientRepository.findById(id).orElse(null);
        if(p == null) red.addFlashAttribute("error", "Error deleting Patient !");
        else {
            patientRepository.delete(p);
            red.addFlashAttribute("success", "Patient deleted successfully !");
        }
        return "redirect:/patients?page=" + page + "&keyword=" + keyword + "&size=" + size;
    }

    @GetMapping("/patients/add")
    public String addPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("success", null);
        model.addAttribute("error", null);
        return "addPatient";
    }

    @PostMapping("/patients/add")
    public String addPatient(@Valid Patient patient, BindingResult res, Model model) {
        if(res.hasErrors()){
            model.addAttribute("error", "Failed to add Patient !");
            return "addPatient";
        }
        patientRepository.save(patient);
        model.addAttribute("success", "Patient added successfully !");
        return "addPatient";
    }

    @GetMapping("/patients/update")
    public String update(@RequestParam Long id, Model model, @RequestParam String keyword,
                                @RequestParam int page, @RequestParam int size) {
        model.addAttribute("success", null);
        model.addAttribute("error", null);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        Patient p = patientRepository.findById(id).orElse(null);
        if(p != null) model.addAttribute("patient", p);
        return "updatePatient";
    }

    @PostMapping("/patients/update")
    public String updatePatient(@Valid Patient patient, BindingResult res, Model model, RedirectAttributes red,
                                @RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        if(res.hasErrors()){
            model.addAttribute("error", "Failed to update Patient !");
            return "updatePatient";
        }
        patientRepository.save(patient);
        red.addFlashAttribute("success", "Patient updated successfully !");
        return "redirect:/patients?page=" + page + "&keyword="+ keyword + "&size=" + size;
    }

}
