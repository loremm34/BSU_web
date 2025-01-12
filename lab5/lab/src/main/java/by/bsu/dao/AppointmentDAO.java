package by.bsu.dao;

import by.bsu.entities.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Logger;

public class AppointmentDAO {

    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());
    private EntityManager em;

    public AppointmentDAO(EntityManager em) {
        this.em = em;
    }


    public List<Appointment> findAll() {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a", Appointment.class);
        List<Appointment> result = query.getResultList();
        LOGGER.info("Получены все Appointment, количество: " + result.size());
        return result;
    }
}
