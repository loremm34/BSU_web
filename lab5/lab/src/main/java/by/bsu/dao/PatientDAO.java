package by.bsu.dao;

import by.bsu.entities.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PatientDAO {

    private EntityManager em;

    public PatientDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Patient patient) {
        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();
    }

    public Patient findById(Long id) {
        return em.find(Patient.class, id);
    }

    public List<Patient> findAll() {
        TypedQuery<Patient> query = em.createQuery("SELECT p FROM Patient p", Patient.class);
        return query.getResultList();
    }

    public void update(Patient patient) {
        em.getTransaction().begin();
        em.merge(patient);
        em.getTransaction().commit();
    }

    public void delete(Long id) {
        em.getTransaction().begin();
        Patient patient = em.find(Patient.class, id);
        if (patient != null) {
            em.remove(patient);
        }
        em.getTransaction().commit();
    }
}
