package DAO;

import Connector.ConnectionPool;
import Entities.Doctor;
import Entities.Patient;
import Entities.Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HospitalDAO {
    private static final Logger logger = Logger.getLogger(HospitalDAO.class.getName());
    private final Hospital hospital = Hospital.getInstance();

    public void addDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (name) VALUES (?)";
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, doctor.getName());
                pstmt.executeUpdate();
                hospital.addDoctor(doctor);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении врача", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, diagnosis) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, patient.getName());
                pstmt.setString(2, patient.getDiagnosis() != null ? patient.getDiagnosis().getDescription() : null);
                pstmt.executeUpdate();
                hospital.addPatient(patient);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении пациента", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT name, diagnosis FROM patients";
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

    public List<Doctor> getAllDoctors() {
        return hospital.getDoctors();
    }

    public List<Patient> getPatientsByDoctor(Doctor doctor) {
        return hospital.getPatientsByDoctor(doctor);
    }
}
