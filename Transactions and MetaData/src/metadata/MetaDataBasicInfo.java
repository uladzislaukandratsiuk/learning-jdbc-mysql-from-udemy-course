package metadata;

import java.sql.*;

public class MetaDataBasicInfo {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    public static void main(String[] args) {

        try (Connection myConn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD)) {

            //  Get metadata
            DatabaseMetaData databaseMetaData = myConn.getMetaData();

            //  Display info about database
            System.out.println("Product name: " + databaseMetaData.getDatabaseProductName());
            System.out.println("Product version: " + databaseMetaData.getDatabaseProductVersion());
            System.out.println();

            //  Display info about JDBC Driver
            System.out.println("JDBC Driver name: " + databaseMetaData.getDriverName());
            System.out.println("JDBC Driver version: " + databaseMetaData.getDriverVersion());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
