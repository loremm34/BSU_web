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
import java.util.List;

public class DoctorController implements IController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private DoctorDAO doctorDAO;

    public DoctorController() {

    }

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
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
