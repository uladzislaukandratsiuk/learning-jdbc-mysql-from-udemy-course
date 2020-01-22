import java.sql.*;

public class JdbcDelete {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    public static final String SELECT_LAST_FIRST_NAME_EMAIL_FROM_EMPLOYEES_WHERE
            = "select last_name, first_name, email from employees where last_name=? and first_name=?";
    public static final String DELETE_FROM_EMPLOYEES
            = "delete from employees where last_name='Doe' and first_name='John'";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement myStmt = myConn.createStatement()) {

            System.out.println("Database connection successful!\n");

            // Call helper method to display the employee's information
            System.out.println("BEFORE THE DELETE...");
            displayEmployee(myConn, "John", "Doe");

            // DELETE the employee
            System.out.println("\nDELETING THE EMPLOYEE: John Doe\n");

            int rowsAffected = myStmt.executeUpdate(DELETE_FROM_EMPLOYEES);

            // Call helper method to display the employee's information
            System.out.println("AFTER THE DELETE..." + "\nRows affected = " + rowsAffected);
            displayEmployee(myConn, "John", "Doe");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayEmployee(Connection myConn, String firstName, String lastName) {

        // Prepare statement
        try (PreparedStatement myStmt = myConn.prepareStatement(SELECT_LAST_FIRST_NAME_EMAIL_FROM_EMPLOYEES_WHERE)) {

            myStmt.setString(1, lastName);
            myStmt.setString(2, firstName);

            // Execute SQL query
            try (ResultSet myRs = myStmt.executeQuery()) {

                boolean found = false;

                // Process result set
                while (myRs.next()) {
                    String theLastName = myRs.getString("last_name");
                    String theFirstName = myRs.getString("first_name");
                    String email = myRs.getString("email");

                    System.out.format("%s %s, %s\n", theFirstName, theLastName, email);
                    found = true;
                }

                if (!found) {
                    System.out.println("Employee NOT FOUND: " + firstName + " " + lastName);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
