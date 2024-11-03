package dao;

import entity.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PatientDAO {

    private EntityManager em;

    public PatientDAO(EntityManager em) {
        this.em = em;
    }

    // Сохранение нового пациента
    public void save(Patient patient) {
        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();
    }

    // Получение пациента по ID
    public Patient findById(Long id) {
        return em.find(Patient.class, id);
    }

    // Получение всех пациентов
    public List<Patient> findAll() {
        TypedQuery<Patient> query = em.createQuery("SELECT p FROM Patient p", Patient.class);
        return query.getResultList();
    }

    // Обновление данных пациента
    public void update(Patient patient) {
        em.getTransaction().begin();
        em.merge(patient);
        em.getTransaction().commit();
    }

    // Удаление пациента
    public void delete(Long id) {
        em.getTransaction().begin();
        Patient patient = em.find(Patient.class, id);
        if (patient != null) {
            em.remove(patient);
        }
        em.getTransaction().commit();
    }
}
