package dao;

import entity.Patient;
import entity.Patient_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDAO {

    private static final Logger LOGGER = Logger.getLogger(PatientDAO.class.getName());
    private EntityManager em;

    public PatientDAO(EntityManager em) {
        this.em = em;
    }

    public Patient findById(Long id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
            Root<Patient> patientRoot = cq.from(Patient.class);
            cq.select(patientRoot).where(cb.equal(patientRoot.get(Patient_.id), id));

            Patient patient = em.createQuery(cq).getSingleResult();
            LOGGER.info("Patient найден: " + patient);
            return patient;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении Patient с ID: " + id, e);
            e.printStackTrace();
            return null;
        }
    }

    public List<Patient> findAll() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
            Root<Patient> patientRoot = cq.from(Patient.class);
            cq.select(patientRoot);

            List<Patient> result = em.createQuery(cq).getResultList();
            LOGGER.info("Получены все Patient, количество: " + result.size());
            return result;
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении всех Patient", e);
            e.printStackTrace();
            return null;
        }
    }
}
