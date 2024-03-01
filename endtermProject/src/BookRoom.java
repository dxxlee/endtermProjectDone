import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BookRoom implements MenuOption {
    @Override
    public void execute(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the number of the room you want to book: ");
        int room_id = scanner.nextInt();
        System.out.print("Enter your username: ");
        String username = scanner.next();

        String sql = "INSERT INTO roompurchase (room_id, username) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, room_id);
            statement.setString(2, username);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Congratulations! You have successfully booked the room.");
            } else {
                System.out.println("Failed to book the room. Please check the number of the room and try again.");
            }
        }
    }
    @Override
    public String getDescription() {
        return "Book a Room";
    }
}
