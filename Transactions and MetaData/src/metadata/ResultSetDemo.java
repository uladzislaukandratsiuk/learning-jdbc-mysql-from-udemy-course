package metadata;

import java.sql.*;

public class ResultSetDemo {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private static final String SELECT_ID_LAST_NAME_FIRST_NAME_SALARY
            = "select id, last_name, first_name, salary from employees";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement myStmt = myConn.createStatement();
             ResultSet myRs = myStmt.executeQuery(SELECT_ID_LAST_NAME_FIRST_NAME_SALARY)) {

            // Get result set metadata
            ResultSetMetaData rsMetaData = myRs.getMetaData();

            // Display info
            int columnCount = rsMetaData.getColumnCount();
            System.out.println("Column count: " + columnCount + "\n");

            for (int column = 1; column <= columnCount; column++) {
                System.out.println("Column name: " + rsMetaData.getColumnName(column));
                System.out.println("Column type name: " + rsMetaData.getColumnTypeName(column));
                System.out.println("Is Nullable: " + rsMetaData.isNullable(column));
                System.out.println("Is Auto Increment: " + rsMetaData.isAutoIncrement(column) + "\n");
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
