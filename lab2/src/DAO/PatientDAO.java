package DAO;

import Connector.JdbcConnector;
import Entities.Patient;
import Entities.Diagnosis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Получение всех пациентов
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient";

        try (Connection conn = JdbcConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient(rs.getInt("id"), rs.getString("name"));
                patients.add(patient);
            }
        }

        return patients;
    }

    // Получение пациента по имени
    public Patient getPatientByName(String name) throws SQLException {
        String sql = "SELECT * FROM Patient WHERE name = ?";
        Patient patient = null;

        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient(rs.getInt("id"), rs.getString("name"));
                }
            }
        }

        return patient;
    }

    public List<Patient> getPatientsByDoctor(int doctorId) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient WHERE doctor_id = ?"; // Запрос для получения пациентов по ID врача

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient(rs.getInt("id"), rs.getString("name"));
                // Установите другие поля, если они есть
                patients.add(patient);
            }
        }

        return patients;
    }

    public void addPatient(String patientName, int doctorId) throws SQLException {
        String sql = "INSERT INTO Patient (name, doctor_id) VALUES (?, ?)"; // Запрос на добавление пациента

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patientName);
            statement.setInt(2, doctorId); // Установка ID врача
            statement.executeUpdate(); // Выполнение запроса
        }
    }

    // Обновление диагноза пациента
    public void updatePatientDiagnosis(int patientId, Diagnosis diagnosis) throws SQLException {
        String sql = "UPDATE Patient SET Diagnosis = ? WHERE id = ?";

        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, diagnosis.getDescription());
            pstmt.setInt(2, patientId);
            pstmt.executeUpdate();
        }
    }
}
