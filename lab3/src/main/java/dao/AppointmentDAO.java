package dao;

import entity.Appointment;
import entity.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AppointmentDAO {

    private EntityManager em;

    public AppointmentDAO(EntityManager em) {
        this.em = em;
    }

    // Сохранение нового назначения
    public void save(Appointment appointment) {
        em.getTransaction().begin();
        em.persist(appointment);
        em.getTransaction().commit();
    }

    // Получение назначения по ID
    public Appointment findById(Long id) {
        return em.find(Appointment.class, id);
    }

    // Получение всех назначений
    public List<Appointment> findAll() {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a", Appointment.class);
        return query.getResultList();
    }

    // Получение всех назначений для конкретного пациента
    public List<Appointment> findByPatient(Patient patient) {
        TypedQuery<Appointment> query = em.createQuery(
                "SELECT a FROM Appointment a WHERE a.patient = :patient", Appointment.class);
        query.setParameter("patient", patient);
        return query.getResultList();
    }

    // Обновление назначения
    public void update(Appointment appointment) {
        em.getTransaction().begin();
        em.merge(appointment);
        em.getTransaction().commit();
    }

    // Удаление назначения
    public void delete(Long id) {
        em.getTransaction().begin();
        Appointment appointment = em.find(Appointment.class, id);
        if (appointment != null) {
            em.remove(appointment);
        }
        em.getTransaction().commit();
    }
}
