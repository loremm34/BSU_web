package dao;

import entity.Appointment;
import entity.Appointment_;
import entity.Patient;
import entity.Patient_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAO {

    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());
    private EntityManager em;

    public AppointmentDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Appointment appointment) {
        try {
            em.getTransaction().begin();
            em.persist(appointment);
            em.getTransaction().commit();
            LOGGER.info("Appointment сохранено: " + appointment);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Ошибка при сохранении Appointment", e);
            e.printStackTrace();
        }
    }

    public Appointment findById(Long id) {
        try {
            Appointment appointment = em.find(Appointment.class, id);
            LOGGER.info("Appointment найдено: " + appointment);
            return appointment;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении Appointment с ID: " + id, e);
            e.printStackTrace();
            return null;
        }
    }

    public List<Appointment> findAll() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
            Root<Appointment> appointmentRoot = cq.from(Appointment.class);
            cq.select(appointmentRoot);

            List<Appointment> result = em.createQuery(cq).getResultList();
            LOGGER.info("Получены все Appointment, количество: " + result.size());
            return result;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении всех Appointment", e);
            e.printStackTrace();
            return null;
        }
    }

    public List<Appointment> findByPatient(Patient patient) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
            Root<Appointment> appointmentRoot = cq.from(Appointment.class);
            Join<Appointment, Patient> patientJoin = appointmentRoot.join(Appointment_.patient);

            cq.where(cb.equal(patientJoin.get(Patient_.id), patient.getId()));

            List<Appointment> result = em.createQuery(cq).getResultList();
            LOGGER.info("Получены Appointment для пациента: " + patient.getId() + ", количество: " + result.size());
            return result;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении Appointment для пациента: " + patient.getId(), e);
            e.printStackTrace();
            return null;
        }
    }

    public void update(Appointment appointment) {
        try {
            em.getTransaction().begin();
            em.merge(appointment);
            em.getTransaction().commit();
            LOGGER.info("Appointment обновлено: " + appointment);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Ошибка при обновлении Appointment", e);
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Appointment appointment = em.find(Appointment.class, id);
            if (appointment != null) {
                em.remove(appointment);
                LOGGER.info("Appointment удалено с ID: " + id);
            } else {
                LOGGER.warning("Appointment с ID: " + id + " не найдено");
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Ошибка при удалении Appointment с ID: " + id, e);
            e.printStackTrace();
        }
    }
}
