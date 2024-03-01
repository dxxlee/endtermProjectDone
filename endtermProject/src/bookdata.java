import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class bookdata {
    private static final String url = "jdbc:postgresql://localhost:5432/Hotel";
    private static final String username = "postgres";
    private static final String password = "D310106";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database server successfully.\n");
            System.out.println("-------------------------------------------------------------------------------------------\n");

            Menu menu = new Menu(scanner) {
                @Override
                public void execute(Connection connection, Scanner scanner) throws SQLException {

                }

                @Override
                public void displayMenu() {
                    System.out.println("Main Menu:");
                    System.out.println("1. Login as Admin");
                    System.out.println("2. View All Rooms");
                    System.out.println("3. Book a Room");
                    System.out.println("4. View User Rooms");
                    System.out.println("5. Exit");
                }

                @Override
                public void handleChoice(int choice, Connection connection) throws SQLException {

                    switch (choice) {
                        case 1:
                            AdminLogin adminLogin = new AdminLogin();
                            adminLogin.execute(connection, scanner);
                            break;
                        case 2:
                            ViewAllRooms viewAllRooms = new ViewAllRooms();
                            viewAllRooms.execute(connection, scanner);
                            break;
                        case 3:
                            BookRoom bookRoom = new BookRoom();
                            bookRoom.execute(connection, scanner);
                            break;
                        case 4:
                            ViewUserRooms viewUserRooms = new ViewUserRooms();
                            viewUserRooms.execute(connection, scanner);
                            break;
                        case 5:
                            System.out.println("Exiting...\n");
                            System.out.println("Goodbye, check us out again!");
                            System.out.println("-------------------------------------------------------------------------------------------");
                            return;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                }
            };

            while (true) {
                menu.displayMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                menu.handleChoice(choice, connection);
            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
