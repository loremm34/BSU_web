package Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;


public class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());
    private static final int MAX_POOL_SIZE = 10;
    private static final BlockingQueue<Connection> connectionPool = new ArrayBlockingQueue<>(MAX_POOL_SIZE);
    private static final Properties props = loadProperties();

    static {
        try {
            for (int i = 0; i < MAX_POOL_SIZE; i++) {
                connectionPool.add(createConnection());
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при создании соединения для пула", e);
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("database.properties")) {
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при загрузке свойств базы данных", e);
        }
        return properties;
    }

    private static Connection createConnection() throws SQLException {
        String url = props.getProperty("db.url");
        return DriverManager.getConnection(url);
    }

    public static Connection getConnection() throws SQLException {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Не удалось получить соединение", e);
            throw new SQLException("Не удалось получить соединение", e);
        }
    }

    // Возврат соединения в пул
    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connectionPool.put(connection);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Ошибка при возврате соединения в пул", e);
            }
        }
    }
}
