package system;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DatabaseConnector.getConnection();
        UserService userService = new UserServiceImpl(connection);

        while (true) {
            System.out.println("1. Вход");
            System.out.println("2. Вывод всех пользователей");
            System.out.println("3. Регистрация");
            System.out.println("4. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    User user = userService.login(scanner);
                    if (user != null) {
                        System.out.println("Добро пожаловать, " + user.getLogin() + "!");
                    }
                }
                case 2 -> {
                    List<User> users = userService.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("Пользователей нет.");
                    } else {
                        users.forEach(System.out::println);
                    }
                }
                case 3 -> userService.register(scanner);
                case 4 -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
