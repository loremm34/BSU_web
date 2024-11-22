package by.bsu.controller;

import java.io.Writer;
import java.util.List;

import by.bsu.dao.DoctorDAO;
import by.bsu.entities.Doctor;
import jakarta.persistence.Persistence;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DoctorController implements IController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private DoctorDAO doctorDAO;

    public DoctorController() {

    }

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        entityManagerFactory = Persistence.createEntityManagerFactory("hospital_db");
        entityManager = entityManagerFactory.createEntityManager();

        doctorDAO = new DoctorDAO(entityManager);

        List<Doctor> Doctors = doctorDAO.findAll();
        ctx.setVariable("Doctors", Doctors);

        // Render the template
        templateEngine.process("doctors/list", ctx, writer);
    }
}
