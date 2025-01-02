package by.bsu.controller;

import by.bsu.entities.Patient;
import by.bsu.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    @GetMapping("/patients/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/add";
    }

    @PostMapping("/patients/add")
    public String addPatient(@ModelAttribute Patient patient) {
        patientRepository.save(patient);
        return "redirect:/patients";
    }
}
