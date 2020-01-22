import java.sql.*;

public class JdbcUpdate {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String UPDATE_EMPLOYEE
            = "update employees set email='john.doe@luv2code.com' where last_name='Doe' and first_name='John'";
    public static final String SELECT_LAST_FIRST_NAME_EMAIL_FROM_EMPLOYEES_WHERE
            = "select last_name, first_name, email from employees where last_name=? and first_name=?";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement myStmt = myConn.createStatement()) {

            System.out.println("Database connection successful!\n");

            // Call helper method to display the employee's information
            System.out.println("BEFORE THE UPDATE...");
            displayEmployee(myConn);

            // UPDATE the employee
            System.out.println("\nEXECUTING THE UPDATE FOR: John Doe\n");

            int rowsAffected = myStmt.executeUpdate(
                    UPDATE_EMPLOYEE);

            // Call helper method to display the employee's information
            System.out.println("AFTER THE UPDATE..." + "\nRows affected = " + rowsAffected);
            displayEmployee(myConn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayEmployee(Connection myConn) {

        // Prepare statement
        try (PreparedStatement myStmt = myConn.prepareStatement(SELECT_LAST_FIRST_NAME_EMAIL_FROM_EMPLOYEES_WHERE)) {

            myStmt.setString(1, "Doe");
            myStmt.setString(2, "John");

            // Execute SQL query
            try (ResultSet myRs = myStmt.executeQuery()) {
                // Process result set
                while (myRs.next()) {
                    String theLastName = myRs.getString("last_name");
                    String theFirstName = myRs.getString("first_name");
                    String email = myRs.getString("email");

                    System.out.format("%s %s, %s\n", theFirstName, theLastName, email);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
