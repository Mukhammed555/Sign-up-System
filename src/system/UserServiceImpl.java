package system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserServiceImpl implements UserService {
    private final Connection connection;

    public UserServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User login(Scanner scanner) {
        System.out.print("Введите логин: ");
        String inputLogin = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String inputPassword = scanner.nextLine();

        String query = "SELECT * FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inputLogin);
            preparedStatement.setString(2, inputPassword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Вход выполнен успешно!");
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            } else {
                System.out.println("Неправильный логин или пароль.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void register(Scanner scanner) {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        String query = "INSERT INTO users (login, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            int rows = preparedStatement.executeUpdate();
            System.out.println(rows > 0 ? "Регистрация успешна!" : "Ошибка регистрации.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
