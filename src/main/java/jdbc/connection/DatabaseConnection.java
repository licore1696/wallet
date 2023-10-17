package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection testDatabaseConnection(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;  // Соединение успешно
        } catch (SQLException e) {
            e.printStackTrace();
            return null;// Ошибка при подключении
        }

    }
}