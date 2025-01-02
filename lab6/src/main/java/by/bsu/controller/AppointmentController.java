package by.bsu.controller;

import by.bsu.entities.Appointment;
import by.bsu.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "appointments/list";
    }
}
