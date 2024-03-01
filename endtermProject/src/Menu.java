import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Menu {
    private Map<Integer, MenuOption> options;
    protected Scanner scanner; // Add this line

    public Menu(Scanner scanner) {
        this.options = new HashMap<>();
        this.scanner = scanner;
        initializeOptions();
    }

    private void initializeOptions() {
        options.put(1, new AdminLogin());
        options.put(2, new ViewAllRooms());
        options.put(3, new BookRoom());
        options.put(4, new ViewUserRooms());
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (Map.Entry<Integer, MenuOption> entry : options.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getDescription());
        }
    }

    public void handleChoice(int choice, Connection connection) throws SQLException {
        MenuOption option = options.get(choice);
        if (option != null) {
            option.execute(connection, scanner);
        } else {
            System.out.println("Invalid choice!");
        }
    }

    public abstract void execute(Connection connection, Scanner scanner) throws SQLException;
}
