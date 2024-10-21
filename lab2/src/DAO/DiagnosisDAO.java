package DAO;

import Connector.JdbcConnector;
import Entities.Diagnosis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiagnosisDAO {

    // Получение диагноза по пациенту
    public Diagnosis getDiagnosisByPatient(int patientId) throws SQLException {
        String sql = "SELECT * FROM Diagnosis WHERE patient_id = ?"; // Запрос для получения диагноза

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Diagnosis(rs.getString("description"));
            }
        }

        return null;
    }

    public void addDiagnosis(String description, int patientId) throws SQLException {
        String sql = "INSERT INTO Diagnosis (description, patient_id) VALUES (?, ?)"; // Запрос на добавление диагноза

        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setInt(2, patientId); // Установка ID пациента
            statement.executeUpdate(); // Выполнение запроса
        }
    }
}
