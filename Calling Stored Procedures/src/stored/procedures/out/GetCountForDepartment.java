package stored.procedures.out;

import java.sql.*;

public class GetCountForDepartment {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    public static final String CALL_GET_COUNT_FOR_DEPARTMENT = "{call get_count_for_department(?, ?)}";

    public static void main(String[] args) {

        String theDepartment = "HR";

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             CallableStatement myStmt = myConn.prepareCall(CALL_GET_COUNT_FOR_DEPARTMENT)) {

            // Set the parameters
            myStmt.setString(1, theDepartment);
            myStmt.registerOutParameter(2, Types.INTEGER);

            // Call stored procedure
            System.out.println("Calling stored procedure.  get_count_for_department('" + theDepartment + "', ?)");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            // Get the value of the OUT parameter
            int theCount = myStmt.getInt(2);

            System.out.println("\nThe count = " + theCount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
