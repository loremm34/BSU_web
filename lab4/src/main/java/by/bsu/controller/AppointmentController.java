package by.bsu.controller;

import java.io.Writer;
import java.util.List;

import by.bsu.dao.AppointmentDAO;
import by.bsu.entities.Appointment;
import jakarta.persistence.Persistence;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppointmentController implements IController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private AppointmentDAO appointmentDAO;

    public AppointmentController() {

    }

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        entityManagerFactory = Persistence.createEntityManagerFactory("hospital_db");
        entityManager = entityManagerFactory.createEntityManager();

        appointmentDAO = new AppointmentDAO(entityManager);

        // Fetch the list of menu items
        List<Appointment> Appointments = appointmentDAO.findAll();
        ctx.setVariable("Appointments", Appointments);

        // Render the template
        templateEngine.process("appointments/list", ctx, writer);
    }
}
