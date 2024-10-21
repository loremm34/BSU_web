package DAO;

import Connector.JdbcConnector;
import Entities.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Получение всех врачей
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor";

        try (Connection conn = JdbcConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(rs.getInt("id"), rs.getString("name"));
                doctors.add(doctor);
            }
        }

        return doctors;
    }

    // Получение врача по ID
    public Doctor getDoctorById(int id) throws SQLException {
        String sql = "SELECT * FROM Doctor WHERE id = ?";
        Doctor doctor = null;

        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor(rs.getInt("id"), rs.getString("name"));
                }
            }
        }

        return doctor;
    }
}