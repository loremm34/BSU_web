package by.bsu.controller;

import by.bsu.dao.AppointmentDAO;
import by.bsu.entities.Appointment;
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

public class AppointmentController implements IController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private AppointmentDAO appointmentDAO;

    public AppointmentController() {

    }

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        entityManagerFactory = Persistence.createEntityManagerFactory("hospital_db");
        entityManager = entityManagerFactory.createEntityManager();

        appointmentDAO = new AppointmentDAO(entityManager);

        List<Appointment> Appointments = appointmentDAO.findAll();
        ctx.setVariable("Appointments", Appointments);

        templateEngine.process("appointments/list", ctx, writer);
    }
}
