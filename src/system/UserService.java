package system;
import java.util.Scanner;
import java.util.List;

public interface UserService {
    User login(Scanner scanner);
    void register(Scanner scanner);
    List<User> getAllUsers();
}

