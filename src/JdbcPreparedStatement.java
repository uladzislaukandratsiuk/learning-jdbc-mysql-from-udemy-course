import java.sql.*;

public class JdbcPreparedStatement {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";
    private static final String SELECT_WHERE_SALARY_AND_DEPARTMENT
            = "select * from employees where salary > ? and department=?";

    public static void main(String[] args) throws SQLException {

        ResultSet myRs = null;

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             PreparedStatement myStmt = myConn.prepareStatement(SELECT_WHERE_SALARY_AND_DEPARTMENT)) {

            System.out.println("Database connection successful!\n");

            // Set the parameters
            myStmt.setDouble(1, 80000);
            myStmt.setString(2, "Legal");

            myRs = myStmt.executeQuery();

            display(myRs);

            System.out.println("\n\nReuse the prepared statement:  salary > 25000,  department = HR");

            // Set the parameters
            myStmt.setDouble(1, 25000);
            myStmt.setString(2, "HR");

            myRs = myStmt.executeQuery();

            display(myRs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            if (myRs != null) {
                myRs.close();
            }
        }
    }

    private static void display(ResultSet myRs) throws SQLException {
        while (myRs.next()) {
            String lastName = myRs.getString("last_name");
            String firstName = myRs.getString("first_name");
            double salary = myRs.getDouble("salary");
            String department = myRs.getString("department");

            System.out.format("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
        }
    }
}
