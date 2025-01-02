package by.bsu.controller;

import by.bsu.entities.Doctor;
import by.bsu.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors/list";
    }

    @GetMapping("/doctors/add")
    public String showAddDoctorForm() {
        return "doctors/add";
    }

    @PostMapping("/doctors/add")
    public String addDoctor(@RequestParam String name, @RequestParam String specialization) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }
}
