import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminLogin implements MenuOption {
    @Override
    public void execute(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username: ");
        String adminUsername = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        if (adminUsername.equals("admin") && password.equals("12345")) {
            System.out.println("Admin login successful!");
            while (true) {
                System.out.println("\nAdmin Features:");
                System.out.println("1. View all users who have booked rooms");
                System.out.println("2. Add a new room");
                System.out.println("3. Update a room info");
                System.out.println("4. Delete a room");
                System.out.println("5. Back to main menu");

                int adminChoice = scanner.nextInt();
                switch (adminChoice) {
                    case 1:
                        viewUsersWithBookedRooms(connection);
                        break;
                    case 2:
                        addRoom(connection, scanner);
                        break;
                    case 3:
                        updateRoom(connection, scanner);
                        break; 
                    case 4:
                        deleteRoom(connection, scanner);
                        break;
                    case 5:

                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } else {
            System.out.println("Wrong login or password. Please try again.");
        }
    }

    private void viewUsersWithBookedRooms(Connection connection) throws SQLException {
        String sql = "SELECT DISTINCT username, room_id FROM roompurchase";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("Users who have booked rooms:");
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("username") + "    Room №" +
                        resultSet.getInt("room_id"));
            }
        }
    }
    private static void addRoom(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter room details:");
        System.out.print("Room number: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Room layout: ");
        String room_layout = scanner.nextLine();

        System.out.print("Price: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Other info: ");
        String options = scanner.nextLine();

        System.out.print("Enter room status (true/false): ");
        boolean status = Boolean.parseBoolean(scanner.next());


        String sql = "INSERT INTO bookdata (id, room_layout, price, options, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setString(2, room_layout);
        statement.setInt(3, price);
        statement.setString(4, options);
        statement.setBoolean(5, status);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new room has been added successfully.");
        }
    }

    private void updateRoom(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter room details for updating:");
        System.out.print("Room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new room status (true/false): ");
        boolean status;
        String statusInput = scanner.nextLine().toLowerCase(); // Convert input to lowercase for case insensitivity
        if (statusInput.equals("true")) {
            status = true;
        } else if (statusInput.equals("false")) {
            status = false;
        } else {
            System.out.println("Invalid input for room status. Please enter 'true' or 'false'.");
            return;
        }

        String sql = "UPDATE bookdata SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, status);
            statement.setInt(2, roomNumber);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Room status updated successfully.");
            } else {
                System.out.println("Failed to update room status. Room number may not exist.");
            }
        }
    }


    private static void deleteRoom(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the number of the room to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM bookdata WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Room has been deleted successfully.");
        } else {
            System.out.println("No room found with the given ID.");
        }
    }

    @Override
    public String getDescription() {
        return "Login as Admin";
    }

}
