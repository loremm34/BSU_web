package DAO;

import Connector.ConnectionPool;
import Entities.Diagnosis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiagnosisDAO {
    private static final Logger logger = Logger.getLogger(DiagnosisDAO.class.getName());

    public Diagnosis getDiagnosisByPatient(int patientId) throws SQLException {
        String sql = "SELECT * FROM Diagnosis WHERE patient_id = ?";
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, patientId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new Diagnosis(rs.getString("description"));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении диагноза", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }

        return null;
    }

    public void addDiagnosis(String description, int patientId) throws SQLException {
        String sql = "INSERT INTO Diagnosis (description, patient_id) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, description);
                pstmt.setInt(2, patientId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении диагноза", e);
            throw e;
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }
}
