package DAO;

import Connector.ConnectionPool;
import Entities.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAO {
    private static final Logger logger = Logger.getLogger(AppointmentDAO.class.getName());

    public List<Appointment> getAppointmentsByPatient(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE patient_id = ?";
        Connection conn = null;

        try {
            // Получаем соединение из пула
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, patientId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        // Создаем экземпляр Appointment на основе результатов запроса
                        Appointment appointment = new Appointment(rs.getInt("id"), rs.getString("description"));
                        appointments.add(appointment);
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении назначений", e);
            throw e; // Пробрасываем исключение дальше
        } finally {
            // Освобождаем соединение
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }

        return appointments;
    }

    public void addAppointment(String description, int patientId) throws SQLException {
        String sql = "INSERT INTO Appointments (description, patient_id) VALUES (?, ?)";
        Connection conn = null;

        try {
            // Получаем соединение из пула
            conn = ConnectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, description);
                pstmt.setInt(2, patientId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении назначения", e);
            throw e; // Пробрасываем исключение дальше
        } finally {
            // Освобождаем соединение
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
    }
}
