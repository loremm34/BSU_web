package by.bsu.controller;

import by.bsu.dao.DoctorDAO;
import by.bsu.entities.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;


public class DoctorAddController implements IController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private DoctorDAO doctorDAO;

    public DoctorAddController() {

    }

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());


        String name = webExchange.getRequest().getParameterValue("Name");
        String specialization = webExchange.getRequest().getParameterValue("Specialization");

        if (name != null && specialization != null) {
            Doctor doctor = new Doctor();
            doctor.setName(name);
            doctor.setSpecialization(specialization);
            entityManagerFactory = Persistence.createEntityManagerFactory("hospital_db");
            entityManager = entityManagerFactory.createEntityManager();

            doctorDAO = new DoctorDAO(entityManager);
            doctorDAO.save(doctor);
            ctx.setVariable("success", true);
        } else {
            ctx.setVariable("error", "All fields are required!");
        }

        templateEngine.process("doctors/add", ctx, writer);
    }
}