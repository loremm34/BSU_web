package DAO;

import Connector.JdbcConnector;
import Entities.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Получение всех назначений по пациенту
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE patient_id = ?";

        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment(rs.getString("description"));
                    appointments.add(appointment);
                }
            }
        }

        return appointments;
    }

    public void addAppointment(String description, int patientId) throws SQLException {
        String sql = "INSERT INTO Appointment (description, patient_id) VALUES (?, ?)"; // Запрос на добавление назначения

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setInt(2, patientId); // Установка ID пациента
            statement.executeUpdate(); // Выполнение запроса
        }
    }

    // Добавление нового назначения
    public void addAppointment(int patientId, String description) throws SQLException {
        String sql = "INSERT INTO Appointment (patient_id, description) VALUES (?, ?)";

        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
        }
    }

    public List<Appointment> getAppointmentsByPatient(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE patient_id = ?"; // Запрос для получения назначений

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                appointments.add(new Appointment(rs.getString("description")));
            }
        }

        return appointments;
    }
}
