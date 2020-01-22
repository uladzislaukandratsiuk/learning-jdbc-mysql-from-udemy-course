package stored.procedures.inout;

import java.sql.*;

public class GreetTheDepartment {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String CALL_GREET_THE_DEPARTMENT = "{call greet_the_department(?)}";

    public static void main(String[] args) {

        String theDepartment = "Legal";

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             CallableStatement myStmt = myConn.prepareCall(CALL_GREET_THE_DEPARTMENT)) {

            myStmt.registerOutParameter(1, Types.VARCHAR);
            myStmt.setString(1, theDepartment);

            // Call stored procedure
            System.out.println("Calling stored procedure.  greet_the_department('" + theDepartment + "')");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            // Get the value of the INOUT parameter
            String theResult = myStmt.getString(1);

            System.out.println("\nThe result = " + theResult);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
