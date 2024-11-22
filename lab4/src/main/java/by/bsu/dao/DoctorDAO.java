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

    // Сохранение нового врача
    public void save(Doctor doctor) {
        em.getTransaction().begin();
        em.persist(doctor);
        em.getTransaction().commit();
    }

    // Получение врача по ID
    public Doctor findById(Long id) {
        return em.find(Doctor.class, id);
    }

    // Получение всех врачей
    public List<Doctor> findAll() {
        TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d", Doctor.class);
        return query.getResultList();
    }

    // Обновление данных врача
    public void update(Doctor doctor) {
        em.getTransaction().begin();
        em.merge(doctor);
        em.getTransaction().commit();
    }

    // Удаление врача
    public void delete(Long id) {
        em.getTransaction().begin();
        Doctor doctor = em.find(Doctor.class, id);
        if (doctor != null) {
            em.remove(doctor);
        }
        em.getTransaction().commit();
    }
}
