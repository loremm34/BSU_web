package dao;

import entity.Doctor;
import entity.Doctor_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorDAO {

    private static final Logger LOGGER = Logger.getLogger(DoctorDAO.class.getName());
    private EntityManager em;

    public DoctorDAO(EntityManager em) {
        this.em = em;
    }

    public Doctor findById(Long id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);
            Root<Doctor> doctorRoot = cq.from(Doctor.class);
            cq.select(doctorRoot).where(cb.equal(doctorRoot.get(Doctor_.id), id));

            Doctor doctor = em.createQuery(cq).getSingleResult();
            LOGGER.info("Doctor найден: " + doctor);
            return doctor;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении Doctor с ID: " + id, e);
            e.printStackTrace();
            return null;
        }
    }

    public List<Doctor> findAll() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);
            Root<Doctor> doctorRoot = cq.from(Doctor.class);
            cq.select(doctorRoot);

            List<Doctor> result = em.createQuery(cq).getResultList();
            LOGGER.info("Получены все Doctor, количество: " + result.size());
            return result;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении всех Doctor", e);
            e.printStackTrace();
            return null;
        }
    }
}
