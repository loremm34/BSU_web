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

import java.util.Date;
import java.util.List;

public class MainController {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private AppointmentDAO appointmentDAO;
    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;

    public MainController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hospital_db");
        entityManager = entityManagerFactory.createEntityManager();

        appointmentDAO = new AppointmentDAO(entityManager);
        doctorDAO = new DoctorDAO(entityManager);
        patientDAO = new PatientDAO(entityManager);
    }

    // Получение всех пациентов
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }

    // Получение всех врачей
    public List<Doctor> getAllDoctors() {
        return doctorDAO.findAll();
    }

    // Получение всех назначений
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.findAll();
    }

    // Получение назначений для конкретного пациента
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        Patient patient = patientDAO.findById(patientId);
        return (patient != null) ? appointmentDAO.findByPatient(patient) : null;
    }

    // Создание нового назначения для пациента
    public boolean createAppointment(Long doctorId, Long patientId, String treatment, Date appointmentDate) {
        Doctor doctor = doctorDAO.findById(doctorId);
        Patient patient = patientDAO.findById(patientId);

        if (doctor != null && patient != null) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setTreatment(treatment);
            appointment.setAppointmentDate(appointmentDate);
            appointmentDAO.save(appointment);
            return true;
        }
        return false;
    }

    // Обновление существующего назначения
    public boolean updateAppointment(Long appointmentId, String treatment, Date appointmentDate) {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment != null) {
            appointment.setTreatment(treatment);
            appointment.setAppointmentDate(appointmentDate);
            appointmentDAO.update(appointment);
            return true;
        }
        return false;
    }

    // Удаление назначения по ID
    public boolean deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment != null) {
            appointmentDAO.delete(appointmentId);
            return true;
        }
        return false;
    }

    // Получение информации о пациенте по ID
    public Patient getPatient(Long patientId) {
        return patientDAO.findById(patientId);
    }

    // Получение информации о враче по ID
    public Doctor getDoctor(Long doctorId) {
        return doctorDAO.findById(doctorId);
    }

    // Закрытие ресурсов
    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
