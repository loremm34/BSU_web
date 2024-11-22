package by.bsu.dao;

import by.bsu.entities.Appointment;
import by.bsu.entities.Patient;
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

    public void save(Appointment appointment) {
        em.getTransaction().begin();
        em.persist(appointment);
        em.getTransaction().commit();
        LOGGER.info("Appointment сохранено: " + appointment);
    }

    public Appointment findById(Long id) {
        Appointment appointment = em.find(Appointment.class, id);
        LOGGER.info("Appointment найдено: " + appointment);
        return appointment;
    }

    public List<Appointment> findAll() {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a", Appointment.class);
        List<Appointment> result = query.getResultList();
        LOGGER.info("Получены все Appointment, количество: " + result.size());
        return result;
    }

    public List<Appointment> findByPatient(Patient patient) {
        TypedQuery<Appointment> query = em.createQuery(
                "SELECT a FROM Appointment a WHERE a.patient = :patient", Appointment.class);
        query.setParameter("patient", patient);
        List<Appointment> result = query.getResultList();
        LOGGER.info("Получены Appointment для пациента: " + patient.getId() + ", количество: " + result.size());
        return result;
    }

    public void update(Appointment appointment) {
        em.getTransaction().begin();
        em.merge(appointment);
        em.getTransaction().commit();
        LOGGER.info("Appointment обновлено: " + appointment);
    }

    public void delete(Long id) {
        em.getTransaction().begin();
        Appointment appointment = em.find(Appointment.class, id);
        if (appointment != null) {
            em.remove(appointment);
            LOGGER.info("Appointment удалено с ID: " + id);
        } else {
            LOGGER.warning("Appointment с ID: " + id + " не найдено");
        }
        em.getTransaction().commit();
    }
}
