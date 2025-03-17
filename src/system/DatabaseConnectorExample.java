package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectorExample {
    public static Connection getConnection() {
        String url = "jdbc:your_database_url";
        String user = "your_username";
        String password = "your_password";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
