import java.sql.*;

public class JdbcTest {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String SELECT_FROM_EMPLOYEES = "select * from employees";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement myStmt = myConn.createStatement()) {

            System.out.println("Database connection successful!\n");

            try (ResultSet myRs = myStmt.executeQuery(SELECT_FROM_EMPLOYEES)) {
                while (myRs.next()) {
                    System.out.println(myRs.getString("last_name") + ", "
                            + myRs.getString("first_name") + ", "
                            + myRs.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}