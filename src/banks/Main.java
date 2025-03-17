package banks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DatabaseConnector.getConnection();

        while (true) {
            System.out.println("1. Вход");
            System.out.println("2. Вывод всех пользователей");
            System.out.println("3. Регистрация");
            System.out.println("4. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login(connection, scanner);
                    break;
                case 2:
                    getAllUsers(connection);
                    break;
                case 3:
                    register(connection, scanner);
                    break;
                case 4:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void login(Connection connection, Scanner scanner) {
        System.out.print("Введите логин: ");
        String inputLogin = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String inputPassword = scanner.nextLine();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            preparedStatement.setString(1, inputLogin);
            preparedStatement.setString(2, inputPassword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Вход выполнен успешно!");
            } else {
                System.out.println("Неправильный логин или пароль.");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getAllUsers(Connection connection) {
        System.out.println("\nСписок всех пользователей:");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("login");
                String password = resultSet.getString("password");
                System.out.println("Username: " + username + ", Password: " + password);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void register(Connection connection, Scanner scanner) {
        System.out.print("Введите логин для новой учетной записи: ");
        String newLogin = scanner.nextLine();
        System.out.print("Введите пароль для новой учетной записи: ");
        String newPassword = scanner.nextLine();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
            preparedStatement.setString(1, newLogin);
            preparedStatement.setString(2, newPassword);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Регистрация выполнена успешно!");
            } else {
                System.out.println("Ошибка при регистрации.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
