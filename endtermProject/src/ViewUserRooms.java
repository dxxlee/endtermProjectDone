import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewUserRooms implements MenuOption {
    @Override
    public void execute(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter your username to view your purchases: ");
        String username = scanner.next();

        String sql = "SELECT p.room_id, c.room_layout, c.price, p.purchase_date " +
                "FROM roompurchase p " +
                "INNER JOIN bookdata c ON p.room_id = c.id " +
                "WHERE p.username=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("Your room purchases:");
                while (resultSet.next()) {
                    int room_id = resultSet.getInt("room_id");
                    String layout = resultSet.getString("room_layout");
                    double price = resultSet.getDouble("price");
                    String purchaseDate = resultSet.getString("purchase_date");
                    System.out.println("Room number: " + room_id + ", Layout: " + layout + ", Price: " + price + "$");
                }
            }
        }
    }
    @Override
    public String getDescription() {
        return "View User Rooms";
    }
}
