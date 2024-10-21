package DAO;

import Connector.ConnectionPool;
import Entities.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorDAO {
    private static final Logger logger = Logger.getLogger(DoctorDAO.class.getName());

    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor";
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Doctor doctor = new Doctor(rs.getInt("id"), rs.getString("name"));
                    doctors.add(doctor);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении списка врачей", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }

        return doctors;
    }

    public Doctor getDoctorById(int id) throws SQLException {
        String sql = "SELECT * FROM Doctor WHERE id = ?";
        Connection conn = null;
        Doctor doctor = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        doctor = new Doctor(rs.getInt("id"), rs.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении врача по ID", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }

        return doctor;
    }
}
