package stored.procedures.result.set;

import java.sql.*;

public class GetEmployeesForDepartment {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    public static final String CALL_GET_EMPLOYEES_FOR_DEPARTMENT = "{call get_employees_for_department(?)}";

    public static void main(String[] args) {

        String theDepartment = "Engineering";

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             CallableStatement myStmt = myConn.prepareCall(CALL_GET_EMPLOYEES_FOR_DEPARTMENT)) {

            // Set the parameter
            myStmt.setString(1, theDepartment);

            // Call stored procedure
            System.out.println("Calling stored procedure.  get_employees_for_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.println("Finished calling stored procedure.\n");

            try (ResultSet myRs = myStmt.getResultSet()) {
                display(myRs);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void display(ResultSet myRs) throws SQLException {
        // Process result set
        while (myRs.next()) {
            String lastName = myRs.getString("last_name");
            String firstName = myRs.getString("first_name");
            double salary = myRs.getDouble("salary");
            String department = myRs.getString("department");

            System.out.printf("%s, %s, %s, %.2f\n", lastName, firstName, department, salary);
        }
    }
}
