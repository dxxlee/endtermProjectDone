import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public interface MenuOption {
    void execute(Connection connection, Scanner scanner) throws SQLException;

    String getDescription();
}
