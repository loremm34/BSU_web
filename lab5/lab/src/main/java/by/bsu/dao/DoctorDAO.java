package by.bsu.dao;

import by.bsu.entities.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DoctorDAO {

    private EntityManager em;

    public DoctorDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Doctor doctor) {
        em.getTransaction().begin();
        em.persist(doctor);
        em.getTransaction().commit();
    }

    public List<Doctor> findAll() {
        TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d", Doctor.class);
        return query.getResultList();
    }


}
