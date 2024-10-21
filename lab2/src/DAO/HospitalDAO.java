package DAO;

import Entities.Doctor;
import Entities.Patient;
import Entities.Hospital;
import Connector.JdbcConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {
    private final Hospital hospital = Hospital.getInstance();

    // Метод для добавления врача в базу данных
    public void addDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (name) VALUES (?)"; // Запрос на добавление врача

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, doctor.getName()); // Установка имени врача
            statement.executeUpdate(); // Выполнение запроса

            // Добавление врача в локальный экземпляр Hospital
            hospital.addDoctor(doctor);
        }
    }

    // Метод для добавления пациента в базу данных
    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, diagnosis) VALUES (?, ?)"; // Запрос на добавление пациента

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patient.getName()); // Установка имени пациента
            statement.setString(2, patient.getDiagnosis() != null ? patient.getDiagnosis().getDescription() : null); // Установка диагноза
            statement.executeUpdate(); // Выполнение запроса

            // Добавление пациента в локальный экземпляр Hospital
            hospital.addPatient(patient);
        }
    }

    // Метод для получения всех пациентов из базы данных
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT name, diagnosis FROM patients"; // Запрос для получения всех пациентов

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Patient patient = new Patient(rs.getInt("id"), rs.getString("name"));

                // Можно добавить логику для установки диагноза, если он есть в базе данных
                // Например, если диагноз хранится в отдельной таблице и его нужно получить
                patients.add(patient);
            }
        }

        return patients;
    }

    // Метод для получения всех врачей из локального экземпляра Hospital
    public List<Doctor> getAllDoctors() {
        return hospital.getDoctors();
    }

    // Метод для получения пациентов по врачу
    public List<Patient> getPatientsByDoctor(Doctor doctor) {
        return hospital.getPatientsByDoctor(doctor);
    }
}
