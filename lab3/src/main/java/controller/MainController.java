package controller;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private AppointmentDAO appointmentDAO;
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;

    public MainController() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hospital_db");
            entityManager = entityManagerFactory.createEntityManager();

            appointmentDAO = new AppointmentDAO(entityManager);
            doctorDAO = new DoctorDAO(entityManager);
            patientDAO = new PatientDAO(entityManager);
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при создании EntityManager или DAO объектов", e);
        }
    }

    public List<Patient> getAllPatients() {
        try {
            return patientDAO.findAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении всех пациентов", e);
            return null;
        }
    }
    public List<Doctor> getAllDoctors() {
        try {
            return doctorDAO.findAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении всех врачей", e);
            return null;
        }
    }

    public List<Appointment> getAllAppointments() {
        try {
            return appointmentDAO.findAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении всех назначений", e);
            return null;
        }
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        try {
            Patient patient = patientDAO.findById(patientId);
            return (patient != null) ? appointmentDAO.findByPatient(patient) : null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при получении назначений для пациента с ID: " + patientId, e);
            return null;
        }
    }

    public boolean createAppointment(Long doctorId, Long patientId, String treatment, Date appointmentDate) {
        try {
            Doctor doctor = doctorDAO.findById(doctorId);
            Patient patient = patientDAO.findById(patientId);

            if (doctor != null && patient != null) {
                Appointment appointment = new Appointment();
                appointment.setDoctor(doctor);
                appointment.setPatient(patient);
                appointment.setTreatment(treatment);
                appointment.setAppointmentDate(appointmentDate);
                appointmentDAO.save(appointment);
                LOGGER.info("Новое назначение успешно создано.");
                return true;
            } else {
                LOGGER.warning("Врач или пациент с указанным ID не найдены.");
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при создании нового назначения", e);
            return false;
        }
    }

    public boolean updateAppointment(Long appointmentId, String treatment, Date appointmentDate) {
        try {
            Appointment appointment = appointmentDAO.findById(appointmentId);
            if (appointment != null) {
                appointment.setTreatment(treatment);
                appointment.setAppointmentDate(appointmentDate);
                appointmentDAO.update(appointment);
                LOGGER.info("Назначение успешно обновлено.");
                return true;
            } else {
                LOGGER.warning("Назначение с указанным ID не найдено.");
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при обновлении назначения с ID: " + appointmentId, e);
            return false;
        }
    }

    public boolean deleteAppointment(Long appointmentId) {
        try {
            Appointment appointment = appointmentDAO.findById(appointmentId);
            if (appointment != null) {
                appointmentDAO.delete(appointmentId);
                LOGGER.info("Назначение успешно удалено.");
                return true;
            } else {
                LOGGER.warning("Назначение с указанным ID не найдено.");
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при удалении назначения с ID: " + appointmentId, e);
            return false;
        }
    }

    public void close() {
        try {
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
            LOGGER.info("Ресурсы успешно закрыты.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при закрытии ресурсов", e);
        }
    }
}
