package metadata;

import java.sql.*;

public class SchemaInfo {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    public static void main(String[] args) throws SQLException {

        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = null;
        String columnNamePattern = null;
        String[] types = null;

        ResultSet myRs = null;

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD)) {

            // Get metadata
            DatabaseMetaData databaseMetaData = myConn.getMetaData();

            // Get list of tables
            System.out.println("List of Tables");
            System.out.println("--------------");


            myRs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern,
                    types);

            while (myRs.next()) {
                System.out.println(myRs.getString("TABLE_NAME"));
            }

            // Get list of columns
            System.out.println("\n\nList of Columns");
            System.out.println("--------------");

            myRs = databaseMetaData.getColumns(catalog, schemaPattern, "employees", columnNamePattern);

            while (myRs.next()) {
                System.out.println(myRs.getString("COLUMN_NAME"));
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                myRs.close();
            }
        }
    }
}
