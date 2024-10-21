package DAO;

import Connector.ConnectionPool;
import Entities.Patient;
import Entities.Diagnosis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDAO {
    private static final Logger logger = Logger.getLogger(PatientDAO.class.getName());

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient";
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Patient patient = new Patient(rs.getInt("id"), rs.getString("name"));
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении списка пациентов", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }

        return patients;
    }

    public Patient getPatientById(int id) throws SQLException {
        String sql = "SELECT * FROM Patient WHERE id = ?";
        Connection conn = null;
        Patient patient = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        patient = new Patient(rs.getInt("id"), rs.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении пациента по ID", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }

        return patient;
    }
    public List<Patient> getPatientsByDoctor(int doctorId) throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patients WHERE doctor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, doctorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Patient patient = new Patient(resultSet.getInt("id"), resultSet.getString("name"));
                patients.add(patient);
            }
        }
        return patients;
    }

    public void addPatient(String patientName, int doctorId) throws SQLException {
        String query = "INSERT INTO Patients (name, doctor_id) VALUES (?, ?)";
        Connection connection = ConnectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientName);
            statement.setInt(2, doctorId);
            statement.executeUpdate();
        }
    }

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patient (name, diagnosis_id) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, patient.getName());
                pstmt.setString(1, patient.getDiagnosis().getDescription());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении пациента", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }
}
